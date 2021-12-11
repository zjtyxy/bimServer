function H5sPlayerWS(t) {
    this.sourceBuffer,
    this.buffer = [],
    this.t,
    this.s,
    this.i,
    this.o,
    this.h,
    this.l = 0,
    this.u = 0,
    this.S = 0,
    this.v = !1,
    this.p = !1,
    this.H = !1,
    this.C,
    this.k = t,
    console.log("Websocket Conf:", t),
    this.P = t.videoid,
    this.W = t.token,
    void 0 === this.P ? (this.R = t.videodom, console.log(t.token, "use dom directly")) : (this.R = document.getElementById(this.P), console.log(t.token, "use videoid")),
    this.s = this.R;
    var s = this.k.protocol + "//" + this.k.host + this.k.rootpath + "api/v1/GetImage?token=" + this.W + "&session=" + this.k.session;
    this.R.setAttribute("poster", s)
}
function H5sPlayerHls(t) {
    this.i,
    this.h,
    this.k = t,
    this.P = t.videoid,
    this.W = t.token,
    this.I,
    this.T = t.hlsver,
    void 0 === this.P ? (this.R = t.videodom, console.log(t.token, "use dom directly")) : (this.R = document.getElementById(this.P), console.log(t.token, "use videoid")),
    this.m = this.R,
    this.m.type = "application/x-mpegURL",
    this.M = 0,
    this.O = 0;
    var s = this.k.protocol + "//" + window.location.host + "/api/v1/GetImage?token=" + this.W + "&session=" + this.k.session;
    this.R.setAttribute("poster", s)
}
function H5sPlayerRTC(t) {
    this.i,
    this.o,
    this.h,
    this.l = 0,
    this.u = 0,
    this.S = 0,
    this.v = !1,
    this.p = !1,
    this.k = t,
    this.P = t.videoid,
    this.W = t.token,
    void 0 === this.P ? (this.R = t.videodom, console.log(t.token, "use dom directly")) : (this.R = document.getElementById(this.P), console.log(t.token, "use videoid")),
    this.s = this.R,
    this.J = null,
    this.N = {
        optional: [{
            DtlsSrtpKeyAgreement: !0
        }]
    },
    this.g = {
        mandatory: {
            offerToReceiveAudio: !0,
            offerToReceiveVideo: !0
        }
    },
    this.A = {
        L: []
    },
    this.B = [];
    var s = this.k.protocol + "//" + this.k.host + this.k.rootpath + "api/v1/GetImage?token=" + this.W + "&session=" + this.k.session;
    this.R.setAttribute("poster", s)
}
function createRTCSessionDescription(t) {
    return console.log("createRTCSessionDescription "),
    new RTCSessionDescription(t)
}
H5sPlayerWS.prototype.D = function () {
    !0 === this.v && (console.log("Reconnect..."), this.G(this.W), this.v = !1)
},
H5sPlayerWS.prototype.U = function (t) {
    var s;
    console.log("H5SWebSocketClient");
    try {
        "http:" == this.k.protocol && (s = "undefined" != typeof MozWebSocket ? new MozWebSocket("ws://" + this.k.host + t) : new WebSocket("ws://" + this.k.host + t)),
        "https:" == this.k.protocol && (console.log(this.k.host), s = "undefined" != typeof MozWebSocket ? new MozWebSocket("wss://" + this.k.host + t) : new WebSocket("wss://" + this.k.host + t)),
        console.log(this.k.host)
    } catch (t) {
        return void alert("error")
    }
    return s
},
H5sPlayerWS.prototype.K = function () {
    if (null !== this.sourceBuffer && void 0 !== this.sourceBuffer) {
        if (0 !== this.buffer.length && !this.sourceBuffer.updating) try {
            var t = this.buffer.shift(),
            s = new Uint8Array(t);
            this.sourceBuffer.appendBuffer(s)
        } catch (t) {
            console.log(t)
        }
    } else
        console.log(this.sourceBuffer, "is null or undefined")
},
H5sPlayerWS.prototype.V = function () {
    try {
        this.i.send("keepalive")
    } catch (t) {
        console.log(t)
    }
},
H5sPlayerWS.prototype.j = function (t) {
    if (!0 !== this.p) return !1 === this.H ? (this.C = String.fromCharCode.apply(null, new Uint8Array(t.data)), this.q(this), void (this.H = !0)) : (this.buffer.push(t.data), void this.K())
},
H5sPlayerWS.prototype.q = function (t) {
    try {
        window.MediaSource = window.MediaSource || window.WebKitMediaSource,
        window.MediaSource || console.log("MediaSource API is not available");
        var s = 'video/mp4; codecs="avc1.42E01E, mp4a.40.2"';
        "MediaSource" in window && MediaSource.isTypeSupported(s) ? console.log("MIME type or codec: ", s) : console.log("Unsupported MIME type or codec: ", s),
        t.t = new window.MediaSource,
        t.s.autoplay = !0,
        console.log(t.P);
        t.s.src = window.URL.createObjectURL(t.t),
        t.s.play(),
        t.t.addEventListener("sourceopen", t.F.bind(t), !1)
    } catch (t) {
        console.log(t)
    }
},
H5sPlayerWS.prototype.F = function () {
    console.log("Add SourceBuffer"),
    this.sourceBuffer = this.t.addSourceBuffer(this.C),
    this.t.duration = 1 / 0,
    this.t.removeEventListener("sourceopen", this.F, !1),
    this.sourceBuffer.addEventListener("updateend", this.K.bind(this), !1)
},
H5sPlayerWS.prototype.G = function (t) {
    this.s.autoplay = !0;
    var s = "api/v1/h5swsapi";
    s = this.k.rootpath + s + "?token=" + t + "&session=" + this.k.session,
    console.log(s),
    this.i = this.U(s),
    console.log("setupWebSocket", this.i),
    this.i.binaryType = "arraybuffer",
    (this.i.X = this).i.onmessage = this.j.bind(this),
    this.i.onopen = function () {
        console.log("wsSocket.onopen", this.X),
        this.X.o = setInterval(this.X.Y.bind(this.X), 1e4),
        this.X.h = setInterval(this.X.V.bind(this.X), 1e3)
    },
    this.i.onclose = function () {
        console.log("wsSocket.onclose", this.X),
        !0 === this.X.p ? console.log("wsSocket.onclose disconnect") : this.X.v = !0,
        this.X.Z(this.X),
        this.X.$(this.X),
        this.X.C = "",
        this.X.H = !1
    }
},
H5sPlayerWS.prototype.Z = function (t) {
    console.log("Cleanup Source Buffer", t);
    try {
        t.sourceBuffer.removeEventListener("updateend", t.K, !1),
        t.sourceBuffer.abort(),
        document.documentMode || /Edge/.test(navigator.userAgent) ? console.log("IE or EDGE!") : t.t.removeSourceBuffer(t.sourceBuffer),
        t.sourceBuffer = null,
        t.t = null,
        t.buffer = []
    } catch (t) {
        console.log(t)
    }
},
H5sPlayerWS.prototype.$ = function (t) {
    console.log("CleanupWebSocket", t),
    clearInterval(t.h),
    clearInterval(t.o),
    t.l = 0,
    t.u = 0,
    t.S = 0
},
H5sPlayerWS.prototype.Y = function () {
    !0 === this.p && (console.log("CheckSourceBuffer has been disconnect", this), clearInterval(this.h), clearInterval(this.o), clearInterval(this._));
    try {
        if (this.sourceBuffer.buffered.length <= 0) {
            if (this.l++, 8 < this.l) return console.log("CheckSourceBuffer Close 1"),
            void this.i.close()
        } else {
            this.l = 0;
            this.sourceBuffer.buffered.start(0);
            var t = this.sourceBuffer.buffered.end(0),
            s = t - this.s.currentTime;
            if (5 < s || s < 0) return console.log("CheckSourceBuffer Close 2", s),
            void this.i.close();
            if (t == this.u) {
                if (this.S++, 3 < this.S) return console.log("CheckSourceBuffer Close 3"),
                void this.i.close()
            } else this.S = 0;
            this.u = t
        }
    } catch (t) {
        console.log(t)
    }
},
H5sPlayerWS.prototype.connect = function () {
    this.G(this.W),
    this._ = setInterval(this.D.bind(this), 3e3)
},
H5sPlayerWS.prototype.disconnect = function () {
    console.log("disconnect", this),
    this.p = !0,
    clearInterval(this._),
    null != this.i && (this.i.close(), this.i = null),
    console.log("disconnect", this)
},
H5sPlayerHls.prototype.U = function (t) {
    var s;
    console.log("H5SWebSocketClient");
    try {
        "http:" == this.k.protocol && (s = "undefined" != typeof MozWebSocket ? new MozWebSocket("ws://" + this.k.host + t) : new WebSocket("ws://" + this.k.host + t)),
        "https:" == this.k.protocol && (console.log(this.k.host), s = "undefined" != typeof MozWebSocket ? new MozWebSocket("wss://" + this.k.host + t) : new WebSocket("wss://" + this.k.host + t)),
        console.log(this.k.host)
    } catch (t) {
        return void alert("error")
    }
    return s
},
H5sPlayerHls.prototype.V = function () {
    try {
        var t = {
            type: "keepalive"
        };
        this.i.send(JSON.stringify(t))
    } catch (t) {
        console.log(t)
    }
},
H5sPlayerHls.prototype.j = function (t) {
    console.log("HLS received ", t.data)
},
H5sPlayerHls.prototype.G = function (t) {
    var s = "api/v1/h5swscmnapi";
    s = this.k.rootpath + s + "?token=" + t,
    console.log(s),
    this.i = this.U(s),
    console.log("setupWebSocket", this.i),
    this.i.binaryType = "arraybuffer",
    (this.i.X = this).i.onmessage = this.j.bind(this),
    this.i.onopen = function () {
        console.log("wsSocket.onopen", this.X),
        this.X.h = setInterval(this.X.V.bind(this.X), 1e3)
    },
    this.i.onclose = function () {
        console.log("wsSocket.onclose", this.X),
        this.X.$(this.X)
    }
},
H5sPlayerHls.prototype.$ = function (t) {
    console.log("H5sPlayerHls CleanupWebSocket", t),
    clearInterval(t.h)
},
H5sPlayerHls.prototype.tt = function () {
    console.log("HLS video.ended", this.m.ended),
    console.log("HLS video.currentTime", this.m.currentTime);
    var t = this.m.currentTime,
    s = t - this.M;
    console.log("HLS diff", s),
    0 === s && this.O++,
    this.M = t,
    3 < this.O && (null != this.i && (this.i.close(), this.i = null), this.G(this.W), console.log("HLS reconnect"), this.m.src = "", this.M = 0, this.O = 0, this.m.src = this.k.protocol + "//" + this.k.host + this.k.rootpath + "hls/" + this.T + "/" + this.W + "/hls.m3u8", this.m.play())
},
H5sPlayerHls.prototype.connect = function () {
    this.G(this.W),
    this.M = 0,
    this.O = 0,
    this.m.onended = function (t) {
        console.log("The End")
    },
    this.m.onpause = function (t) {
        console.log("Pause")
    },
    this.m.onplaying = function (t) {
        console.log("Playing")
    },
    this.m.onseeking = function (t) {
        console.log("seeking")
    },
    this.m.onvolumechange = function (t) {
        console.log("volumechange")
    },
    this.m.src = this.k.protocol + "//" + this.k.host + this.k.rootpath + "hls/" + this.T + "/" + this.W + "/hls.m3u8",
    this.m.play(),
    this.I = setInterval(this.tt.bind(this), 3e3)
},
H5sPlayerHls.prototype.disconnect = function () {
    clearInterval(this.I),
    this.M = 0,
    this.O = 0,
    null != this.i && (this.i.close(), this.i = null),
    console.log("disconnect", this)
},
H5sPlayerRTC.prototype.D = function () {
    !0 === this.v && (console.log("Reconnect..."), this.G(this.W), this.v = !1)
},
H5sPlayerRTC.prototype.U = function (t) {
    var s;
    console.log("H5SWebSocketClient");
    try {
        "http:" == this.k.protocol && (s = "undefined" != typeof MozWebSocket ? new MozWebSocket("ws://" + this.k.host + t) : new WebSocket("ws://" + this.k.host + t)),
        "https:" == this.k.protocol && (console.log(this.k.host), s = "undefined" != typeof MozWebSocket ? new MozWebSocket("wss://" + this.k.host + t) : new WebSocket("wss://" + this.k.host + t)),
        console.log(this.k.host)
    } catch (t) {
        return void alert("error")
    }
    return s
},
H5sPlayerRTC.prototype.V = function () {
    try {
        var t = {
            type: "keepalive"
        };
        this.i.send(JSON.stringify(t))
    } catch (t) {
        console.log(t)
    }
},
H5sPlayerRTC.prototype.st = function (t) {
    if (t.candidate) {
        var s;
        console.log("onIceCandidate currentice", t.candidate),
        s = t.candidate,
        console.log("onIceCandidate currentice", s, JSON.stringify(s));
        var e = JSON.parse(JSON.stringify(s));
        e.type = "remoteice",
        console.log("onIceCandidate currentice new", e, JSON.stringify(e)),
        this.i.send(JSON.stringify(e))
    } else console.log("End of candidates.")
},
H5sPlayerRTC.prototype.et = function (t) {
    var s;
    console.log("Remote track added:" + JSON.stringify(t)),
    s = t.it ? t.it[0] : t.stream;
    var e = this.R;
    e.src = URL.createObjectURL(s),
    e.play()
},
H5sPlayerRTC.prototype.ot = function () {
    console.log("createPeerConnection  config: " + JSON.stringify(this.A) + " option:" + JSON.stringify(this.N));
    var s = new RTCPeerConnection(this.A, this.N),
    e = this;
    return s.onicecandidate = function (t) {
        e.st.call(e, t)
    },
    void 0 !== s.nt ? s.nt = function (t) {
        e.et.call(e, t)
    } : s.onaddstream = function (t) {
        e.et.call(e, t)
    },
    s.oniceconnectionstatechange = function (t) {
        console.log("oniceconnectionstatechange  state: " + s.iceConnectionState)
    },
    console.log("Created RTCPeerConnnection with config: " + JSON.stringify(this.A) + "option:" + JSON.stringify(this.N)),
    s
},
H5sPlayerRTC.prototype.ht = function (t) {
    console.log("ProcessRTCOffer", t);
    try {
        this.J = this.ot(),
        this.B.length = 0;
        var s = this;
        this.J.setRemoteDescription(createRTCSessionDescription(t)),
        this.J.createAnswer(this.g).then(function (t) {
            console.log("Create answer:" + JSON.stringify(t)),
            s.J.setLocalDescription(t,
            function () {
                console.log("ProcessRTCOffer createAnswer", t),
                s.i.send(JSON.stringify(t))
            },
            function () { })
        },
        function (t) {
            alert("Create awnser error:" + JSON.stringify(t))
        })
    } catch (t) {
        this.disconnect(),
        alert("connect error: " + t)
    }
},
H5sPlayerRTC.prototype.ct = function (t) {
    console.log("ProcessRemoteIce", t);
    try {
        var s = new RTCIceCandidate({
            sdpMLineIndex: t.sdpMLineIndex,
            candidate: t.candidate
        });
        console.log("ProcessRemoteIce", s),
        console.log("Adding ICE candidate :" + JSON.stringify(s)),
        this.J.addIceCandidate(s,
        function () {
            console.log("addIceCandidate OK")
        },
        function (t) {
            console.log("addIceCandidate error:" + JSON.stringify(t))
        })
    } catch (t) {
        alert("connect ProcessRemoteIce error: " + t)
    }
},
H5sPlayerRTC.prototype.j = function (t) {
    console.log("RTC received ", t.data);
    var s = JSON.parse(t.data);
    console.log("Get Message type ", s.type),
    "offer" === s.type && (console.log("Process Message type ", s.type), this.ht(s)),
    "remoteice" === s.type && (console.log("Process Message type ", s.type), this.ct(s))
},
H5sPlayerRTC.prototype.G = function (t) {
    this.s.autoplay = !0;
    var s = "api/v1/h5srtcapi";
    s = this.k.rootpath + s + "?token=" + t,
    console.log(s),
    this.i = this.U(s),
    console.log("setupWebSocket", this.i),
    this.i.binaryType = "arraybuffer",
    (this.i.X = this).i.onmessage = this.j.bind(this),
    this.i.onopen = function () {
        console.log("wsSocket.onopen", this.X);
        var t = {
            type: "open"
        };
        this.X.i.send(JSON.stringify(t)),
        this.X.h = setInterval(this.X.V.bind(this.X), 1e3)
    },
    this.i.onclose = function () {
        console.log("wsSocket.onclose", this.X),
        !0 === this.X.p ? console.log("wsSocket.onclose disconnect") : this.X.v = !0,
        this.X.$(this.X)
    }
},
H5sPlayerRTC.prototype.$ = function (t) {
    console.log("CleanupWebSocket", t),
    clearInterval(t.h),
    t.l = 0,
    t.u = 0,
    t.S = 0
},
H5sPlayerRTC.prototype.connect = function () {
    this.G(this.W),
    this._ = setInterval(this.D.bind(this), 3e3)
},
H5sPlayerRTC.prototype.disconnect = function () {
    console.log("disconnect", this),
    this.p = !0,
    clearInterval(this._),
    null != this.i && (this.i.close(), this.i = null),
    console.log("disconnect", this)
};