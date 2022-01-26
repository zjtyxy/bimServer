/**
 * Copyright © 2016-2021 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ciat.bim.server.edge.rpc.fetch;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class AdminSettingsEdgeEventFetcher implements EdgeEventFetcher {

//    private static final ObjectMapper mapper = new ObjectMapper();
//
//    private final AdminSettingsService adminSettingsService;
//    private final Configuration freemarkerConfig;
//
//    private static Pattern startPattern = Pattern.compile("<div class=\"content\".*?>");
//    private static Pattern endPattern = Pattern.compile("<div class=\"footer\".*?>");
//
//    private static List<String> templatesNames = Arrays.asList(
//            "account.activated.ftl",
//            "account.lockout.ftl",
//            "activation.ftl",
//            "password.was.reset.ftl",
//            "reset.password.ftl",
//            "test.ftl");
//
//    // TODO: fix format of next templates
//    // "state.disabled.ftl",
//    // "state.enabled.ftl",
//    // "state.warning.ftl",
//
//    @Override
//    public PageLink getPageLink(int pageSize) {
//        return null;
//    }
//
//    @Override
//    public PageData<EdgeEvent> fetchEdgeEvents(TenantId tenantId, Edge edge, PageLink pageLink) throws Exception {
//        List<EdgeEvent> result = new ArrayList<>();
//
//        AdminSettings systemMailSettings = adminSettingsService.findAdminSettingsByKey(TenantId.SYS_TENANT_ID, "mail");
//        result.add(EdgeEventUtils.constructEdgeEvent(tenantId, edge.getId(), EdgeEventType.ADMIN_SETTINGS,
//                EdgeEventActionType.UPDATED, null, mapper.valueToTree(systemMailSettings)));
//
//        AdminSettings tenantMailSettings = convertToTenantAdminSettings(systemMailSettings.getKey(), (ObjectNode) systemMailSettings.getJsonValue());
//        result.add(EdgeEventUtils.constructEdgeEvent(tenantId, edge.getId(), EdgeEventType.ADMIN_SETTINGS,
//                EdgeEventActionType.UPDATED, null, mapper.valueToTree(tenantMailSettings)));
//
//        AdminSettings systemMailTemplates = loadMailTemplates();
//        result.add(EdgeEventUtils.constructEdgeEvent(tenantId, edge.getId(), EdgeEventType.ADMIN_SETTINGS,
//                EdgeEventActionType.UPDATED, null, mapper.valueToTree(systemMailTemplates)));
//
//        AdminSettings tenantMailTemplates = convertToTenantAdminSettings(systemMailTemplates.getKey(), (ObjectNode) systemMailTemplates.getJsonValue());
//        result.add(EdgeEventUtils.constructEdgeEvent(tenantId, edge.getId(), EdgeEventType.ADMIN_SETTINGS,
//                EdgeEventActionType.UPDATED, null, mapper.valueToTree(tenantMailTemplates)));
//
//        // @voba - returns PageData object to be in sync with other fetchers
//        return new PageData<>(result, 1, result.size(), false);
//    }
//
//    private AdminSettings loadMailTemplates() throws Exception {
//        Map<String, Object> mailTemplates = new HashMap<>();
//        for (String templatesName : templatesNames) {
//            Template template = freemarkerConfig.getTemplate(templatesName);
//            if (template != null) {
//                String name = validateName(template.getName());
//                Map<String, String> mailTemplate = getMailTemplateFromFile(template.toString());
//                if (mailTemplate != null) {
//                    mailTemplates.put(name, mailTemplate);
//                } else {
//                    log.error("Can't load mail template from file {}", template.getName());
//                }
//            }
//        }
//        AdminSettings adminSettings = new AdminSettings();
//        adminSettings.setId(new AdminSettingsId(Uuids.timeBased()));
//        adminSettings.setKey("mailTemplates");
//        adminSettings.setJsonValue(mapper.convertValue(mailTemplates, JsonNode.class));
//        return adminSettings;
//    }
//
//    private Map<String, String> getMailTemplateFromFile(String stringTemplate) {
//        Map<String, String> mailTemplate = new HashMap<>();
//        Matcher start = startPattern.matcher(stringTemplate);
//        Matcher end = endPattern.matcher(stringTemplate);
//        if (start.find() && end.find()) {
//            String body = StringUtils.substringBetween(stringTemplate, start.group(), end.group()).replaceAll("\t", "");
//            String subject = StringUtils.substringBetween(body, "<h2>", "</h2>");
//            mailTemplate.put("subject", subject);
//            mailTemplate.put("body", body);
//        } else {
//            return null;
//        }
//        return mailTemplate;
//    }
//
//    private String validateName(String name) throws Exception {
//        StringBuilder nameBuilder = new StringBuilder();
//        name = name.replace(".ftl", "");
//        String[] nameParts = name.split("\\.");
//        if (nameParts.length >= 1) {
//            nameBuilder.append(nameParts[0]);
//            for (int i = 1; i < nameParts.length; i++) {
//                String word = WordUtils.capitalize(nameParts[i]);
//                nameBuilder.append(word);
//            }
//            return nameBuilder.toString();
//        } else {
//            throw new Exception("Error during filename validation");
//        }
//    }
//
//    private AdminSettings convertToTenantAdminSettings(String key, ObjectNode jsonValue) {
//        AdminSettings tenantMailSettings = new AdminSettings();
//        jsonValue.put("useSystemMailSettings", true);
//        tenantMailSettings.setJsonValue(jsonValue);
//        tenantMailSettings.setKey(key);
//        return tenantMailSettings;
//    }
}
