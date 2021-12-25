package org.jeecg.modules.bim.service.impl;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.bim.entity.BimModelAttrs;
import org.jeecg.modules.bim.mapper.BimModelAttrsMapper;
import org.jeecg.modules.bim.service.IBimModelAttrsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 模型属性
 * @Author: jeecg-boot
 * @Date:   2021-12-20
 * @Version: V1.0
 */
@Service
public class BimModelAttrsServiceImpl extends ServiceImpl<BimModelAttrsMapper, BimModelAttrs> implements IBimModelAttrsService {

	@Override
	public void addBimModelAttrs(BimModelAttrs bimModelAttrs) {
	   //新增时设置hasChild为0
	    bimModelAttrs.setHasChild(IBimModelAttrsService.NOCHILD);
		if(oConvertUtils.isEmpty(bimModelAttrs.getParentId())){
			bimModelAttrs.setParentId(IBimModelAttrsService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			BimModelAttrs parent = baseMapper.selectById(bimModelAttrs.getParentId());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(bimModelAttrs);
	}

	@Override
	public void updateBimModelAttrs(BimModelAttrs bimModelAttrs) {
		BimModelAttrs entity = this.getById(bimModelAttrs.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getParentId();
		String new_pid = bimModelAttrs.getParentId();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				bimModelAttrs.setParentId(IBimModelAttrsService.ROOT_PID_VALUE);
			}
			if(!IBimModelAttrsService.ROOT_PID_VALUE.equals(bimModelAttrs.getParentId())) {
				baseMapper.updateTreeNodeStatus(bimModelAttrs.getParentId(), IBimModelAttrsService.HASCHILD);
			}
		}
		baseMapper.updateById(bimModelAttrs);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBimModelAttrs(String id) throws JeecgBootException {
		//查询选中节点下所有子节点一并删除
        id = this.queryTreeChildIds(id);
        if(id.indexOf(",")>0) {
            StringBuffer sb = new StringBuffer();
            String[] idArr = id.split(",");
            for (String idVal : idArr) {
                if(idVal != null){
                    BimModelAttrs bimModelAttrs = this.getById(idVal);
                    String pidVal = bimModelAttrs.getParentId();
                    //查询此节点上一级是否还有其他子节点
                    List<BimModelAttrs> dataList = baseMapper.selectList(new QueryWrapper<BimModelAttrs>().eq("parent_id", pidVal).notIn("id",Arrays.asList(idArr)));
                    if((dataList == null || dataList.size()==0) && !Arrays.asList(idArr).contains(pidVal)
                            && !sb.toString().contains(pidVal)){
                        //如果当前节点原本有子节点 现在木有了，更新状态
                        sb.append(pidVal).append(",");
                    }
                }
            }
            //批量删除节点
            baseMapper.deleteBatchIds(Arrays.asList(idArr));
            //修改已无子节点的标识
            String[] pidArr = sb.toString().split(",");
            for(String pid : pidArr){
                this.updateOldParentNode(pid);
            }
        }else{
            BimModelAttrs bimModelAttrs = this.getById(id);
            if(bimModelAttrs==null) {
                throw new JeecgBootException("未找到对应实体");
            }
            updateOldParentNode(bimModelAttrs.getParentId());
            baseMapper.deleteById(id);
        }
	}

	@Override
    public List<BimModelAttrs> queryTreeListNoPage(QueryWrapper<BimModelAttrs> queryWrapper) {
        List<BimModelAttrs> dataList = baseMapper.selectList(queryWrapper);
        List<BimModelAttrs> mapList = new ArrayList<>();
        for(BimModelAttrs data : dataList){
            String pidVal = data.getParentId();
            //递归查询子节点的根节点
            if(pidVal != null && !"0".equals(pidVal)){
                BimModelAttrs rootVal = this.getTreeRoot(pidVal);
                if(rootVal != null && !mapList.contains(rootVal)){
                    mapList.add(rootVal);
                }
            }else{
                if(!mapList.contains(data)){
                    mapList.add(data);
                }
            }
        }
        return mapList;
    }

    @Override
    public void updateTree() {
        baseMapper.updateTree();
    }

    /**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!IBimModelAttrsService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<BimModelAttrs>().eq("parent_id", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, IBimModelAttrsService.NOCHILD);
			}
		}
	}

	/**
     * 递归查询节点的根节点
     * @param pidVal
     * @return
     */
    private BimModelAttrs getTreeRoot(String pidVal){
        BimModelAttrs data =  baseMapper.selectById(pidVal);
        if(data != null && !"0".equals(data.getParentId())){
            return this.getTreeRoot(data.getParentId());
        }else{
            return data;
        }
    }

    /**
     * 根据id查询所有子节点id
     * @param ids
     * @return
     */
    private String queryTreeChildIds(String ids) {
        //获取id数组
        String[] idArr = ids.split(",");
        StringBuffer sb = new StringBuffer();
        for (String pidVal : idArr) {
            if(pidVal != null){
                if(!sb.toString().contains(pidVal)){
                    if(sb.toString().length() > 0){
                        sb.append(",");
                    }
                    sb.append(pidVal);
                    this.getTreeChildIds(pidVal,sb);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 递归查询所有子节点
     * @param pidVal
     * @param sb
     * @return
     */
    private StringBuffer getTreeChildIds(String pidVal,StringBuffer sb){
        List<BimModelAttrs> dataList = baseMapper.selectList(new QueryWrapper<BimModelAttrs>().eq("parent_id", pidVal));
        if(dataList != null && dataList.size()>0){
            for(BimModelAttrs tree : dataList) {
                if(!sb.toString().contains(tree.getId())){
                    sb.append(",").append(tree.getId());
                }
                this.getTreeChildIds(tree.getId(),sb);
            }
        }
        return sb;
    }

}
