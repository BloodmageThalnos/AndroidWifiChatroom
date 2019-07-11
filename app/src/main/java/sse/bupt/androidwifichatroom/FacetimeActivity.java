package sse.bupt.androidwifichatroom;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;

import java.lang.ref.WeakReference;

import static com.tencent.trtc.TRTCCloudDef.TRTC_APP_SCENE_VIDEOCALL;

public class FacetimeActivity extends AppCompatActivity {
    Handler mHandler;
    public static final int MESSAGE_LOG = 0x400 + 1;
    public static final int MESSAGE_NEWMSG = 0x400 + 2;

    TRTCCloudListener trtcListener;
    TRTCCloud trtcCloud;
    TRTCCloudDef.TRTCParams trtcParams;
    TXCloudVideoView myView;
    TXCloudVideoView his_or_her_or_its_View;

    public void log(String string) {
        Message message = mHandler.obtainMessage(MESSAGE_LOG, string);
        message.sendToTarget();
    }

    // 继承 TRTCCloudListener 回调
    static class TRTCCloudListenerImpl extends TRTCCloudListener {
        public WeakReference<FacetimeActivity> mContext;
        public TRTCCloudListenerImpl(FacetimeActivity activity) {
            super();
            mContext = new WeakReference<>(activity);
        }

        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            FacetimeActivity activity = mContext.get();
            if (activity != null) {
                activity.log("sdk callback onError("+errCode+"): "+errMsg);
            }
        }

        @Override
        public void onEnterRoom(long elapsed) {
            FacetimeActivity activity = mContext.get();
            if (activity != null) {
                activity.log("room entered.");
            }
        }
    }

    void enterRoom(){
        trtcParams = new TRTCCloudDef.TRTCParams();
        trtcParams.sdkAppId = 1400230187;
        trtcParams.userId   = ""+(System.currentTimeMillis()%9+1);
        String[] userSigList = {
                ""
                ,"eJxlz01PgzAYB-A7n6Lp2bi22G0u8YQLm4KLbHSJl4aUAt0CNKUKi-G7b6JmTXwOz*X3z-Py6QEA4C7a3mZCtO*N5fakJQQLABG8uaLWKueZ5b7J-6EctDKSZ4WVZkRMKSUIuRmVy8aqQv0lHOryIx-n-8gdQsRHeD5zI6ocMV6mwfr10Uxk2L-VqIxPh65iL2mAZB20KX5mxZAk0dAPKqn0bBD9usyOdFMK8xSFwZ4c2ARtVrutDIWaxwzvl13r015U-orR9MFZaVUtf5*ZEjq9v3RHP6TpVNuMAYIwxZeTvwt6X94ZYZZbxg__"
                ,"eJxlj9FOgzAUhu95CtJbjBZYmZh4I9mWxiFb1rnMm6ahXT3TAekKkRnfXWVLbOK5-b5zzv9-er7vIzZfXYuyrNvKcts3Cvl3PsLo6g82DUguLI*N-AfVRwNGcbGzygwwJIREGLsOSFVZ2MHFiBx0lG98uH-eHWEcxTi8HbsK6AHmk3VGl1mrtwsarFdiwpigdU6K4Cl*XpTCmOm7qE1XpHT*aDYjsqT6YUxh1qfTU5GJ2Y3e79kWOt2R-BS8HGT6Ci1jslVBv9H3zksLB3Upk0QkSZPEzdwpc4S6OnfBIQl-Iv8O8r68bx1xW8s_"
                ,"eJxlj11PgzAUhu-5FQ23Gj2lKwMTL-iYZHPoCBoTbpqOdqRuQoXqhmb-XcUlknhun*c95z2fFkLIfljmF7wsm7faMNNraaMrZIN9-ge1VoJxw0gr-kF50KqVjG*MbAeIKaUOwNhRQtZGbdTJICPUiS0b9v9mJwAOAexNx4qqBpjOHqN5FqmPuCjB59E6uylNfTAz8Pe7*8SDfZN2d5qTp6RPSdCn1bwq4HkXNmWVJoIXy7OY1Dpe89Xi9nU6acLwcpvleJHT0AuC69FJo17k6RnXoa7veuPO77LtVFMPggOY4u-KP2NbR*sL3*talg__"
                ,"eJxlj9FOgzAUhu95CsL1YlpYYXg3NqNVGsNcCNtNw9YCzQol0BHQ*O4qLrGJ5-b7zvn-82HZtu3s47e7-HxW10ZTPbXcse9tBziLP9i2gtFcU69j-yAfW9FxmheadzOECCEXANMRjDdaFOJmLA3Uswud7--uLgFwPQBXgamIcobk4bDByUa*eqQaYHrZRjBIZTwlRywDfXonavKUf4j0ap1prEqS4GpNKkl09DScXM7isBHw8RnvM1DIsbum-cuoduUum2p*RLURqUXNb8-4LvJDP0QGHXjXC9XMggsggt*Vf8axPq0va6dchw__"
                ,"eJxlj11PgzAUhu-5FU1vNabt6FCTXYwNFo0kg3WbvSIMynJc1iF0fMz431VcIonn9nnec97zYSGEsHhZ3SVpejprE5uuUBg9Ikzw7R8sCsjixMSjMvsHVVtAqeIkN6rsIeWcM0KGDmRKG8jhavABqrJD3O--zdqEsBGh985QgX0PAy*cPc26NoVVpHKjxaYlezcS-vul3jl2bUfPy*p4WPiE5W-nzXQKrvTlzdpdBMJVr0vhpfO1DHSxdUPPeF7Y2Y2Uu2o7b8BvJpPBSQNHdX1mzPj4waHDQrUqKzjpXmCEcvpd*Wew9Wl9AWaDW-w_"
                ,"eJxlj11PgzAUhu-5FaS3M6a0lDLv9qEOxqKOmbndNLgWVhxQS1mcxv*u4hKbeG6f55z3PR*O67pglaSX2W7XdLVh5qQEcK9cAMHFH1RKcpYZhjX-B8WbklqwLDdC99AjhCAIbUdyURuZy7MRWKjlL6y--7vrQ4gw9EJqK7Lo4eJ6M4kepkn0GG-FehW9jg5dN1w*N0d0E1a0W5T*U3w7fh*0ySQPi3taRPvRPB7gsiindzBdopQc4Fbt2zlBVPINX-sVrU9jPCtnjdJWpJGVOD8TIBIMKUYWPQrdyqbuBQQ94n1X-hngfDpfwJ5auQ__"
                ,"eJxlj8FSgzAQhu88BZOrjoZAivFWqxUsdbR0xuIlE5sgWynQENpSx3dXsTPiuNfv2-3-fbds20bzKD4Ty2XZFIabtlLIvrQRRqe-sKpAcmG4q*U-qPYVaMVFapTuoEMpJRj3HZCqMJDC0fB7qJZvvLv-s*thTFzsXPxR4LWD05tkFD5euys5z2En2dNtrjeRO521YybSJMuumpda35XZcz0UTJr7XZgNJw8j5YarNmZRHCxMUB6KctZQEfgHD4if7Ncn4-PNBNQi70UaWKvjMwNCB8z3vB7dKl1DWXQCwQ51vip-D7I*rE*011y1"
                ,"eJxlj9FqgzAUhu99iuBtx0iiSexgF62TKqyMMVfa3QRnop4WrdNULGPv3s0VFti5-b5z-v98OgghN318uc3y-HhqjDTnVrvoDrnYvfmDbQtKZkZ6nfoH9dhCp2VWGN1NkDDGKMa2A0o3Bgq4GoGFenWQ0-3fXR9j6mESCFuBcoLr6DVMlnGazmDGNyRbx-th9Hdic-ZZ1NNE7N-VMoFyUB-1G0l2C4gW5Vbzh4CHq*cVD4shjyiPq1B4rEqH0yHfMqifmrGsYgjurUgDtb4*wynjc8HmFh1018OxmQSKCSPflX-Gdb6cC3bsWk4_"
                ,"eJxlj9FOgzAUhu95iqa3Gi0gdHhHlk1RQIVFM29IoR2ebUDTlUVmfHcdW2ITz*33nfP-58tCCOFFnF*xqur6Vhd6kAKjW4QJvvyDUgIvmC5cxf9B8SlBiYKttFAjtD3PcwgxHeCi1bCCsxEYaMc3xXj-tHtDiOMSe0JNBeoRJrPlNHqZprP*kSbLRSZI*ZT6fbveTu5omUKVH*a*Ku*jjl3sMwUsjD7Ch2a73gRuPDT1c8VL3aVDWAb0TV7D67x1M4iz6FAn70zlRqSGRpyf8R3PDyh1DLoXagddOwoOsT37t-JxsPVt-QBcxVxD"
        };
        trtcParams.userSig  = userSigList[Integer.parseInt(trtcParams.userId)];
        trtcParams.roomId   = 999; //输入您想进入的房间
        trtcCloud.enterRoom(trtcParams, TRTC_APP_SCENE_VIDEOCALL);

        trtcCloud.startLocalPreview(true, myView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facetime);


        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch(message.what){
                    case MESSAGE_LOG:
                        Toast.makeText(getApplicationContext(), (String)message.obj, Toast.LENGTH_SHORT).show();
                        break;
                    case MESSAGE_NEWMSG:
                        break;
                }
            }
        };


        myView = new TXCloudVideoView(this);
        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.VideoLayout_mine);
        linearLayout.addView(myView);

        his_or_her_or_its_View = new TXCloudVideoView(this);
        RelativeLayout linearLayout2 = (RelativeLayout) findViewById(R.id.VideoLayout_opposite);
        linearLayout2.addView(his_or_her_or_its_View);

    }

    @Override
    protected void onResume(){
        super.onResume();

        trtcListener = new TRTCCloudListenerImpl(this){
            @Override
            public void onUserVideoAvailable(final String userId, boolean available){
                FacetimeActivity activity = mContext.get();
                if (activity != null) {
                    if (available) {
                        log("User video available success.");
                        trtcCloud.setRemoteViewFillMode(userId, TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FIT);
                        trtcCloud.startRemoteView(userId, his_or_her_or_its_View);
                    } else {
                        //停止观看画面
                        trtcCloud.stopRemoteView(userId);
                    }
                }
            }
        };
        trtcCloud = TRTCCloud.sharedInstance(this);
        trtcCloud.setListener(trtcListener);

        log("onResume in FacetimeActivity");
        enterRoom();
    }

    @Override
    protected  void onPause(){
        super.onPause();
        log("onPause in FacetimeActivity");
        trtcCloud.stopLocalPreview();
        trtcCloud.exitRoom();

        log("onDestroy in FacetimeActivity");
        if (trtcCloud != null) {
            trtcCloud.setListener(null);
        }
        trtcCloud = null;
        TRTCCloud.destroySharedInstance();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
