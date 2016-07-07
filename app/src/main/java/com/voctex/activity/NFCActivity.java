package com.voctex.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.voctex.R;
import com.voctex.base.BaseActivity;

import java.io.IOException;

public class NFCActivity extends BaseActivity {


//    private CheckBox mWriteData;
//    private NfcAdapter mNfcAdapter;
//    private PendingIntent mPendingIntent;


    private NfcAdapter mNfcAdapter;
    private String[][] techList;
    private IntentFilter[] intentFilters;
    private PendingIntent mPendingIntent;
    private Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        if (mNfcAdapter == null) {
//            Toast.makeText(this, "设备不支持NFC！", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }
//        if (!mNfcAdapter.isEnabled()) {
//            Toast.makeText(this, "请在系统设置中先启用NFC功能！", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }
//        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);

        //获取nfc适配器
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //定义程序可以兼容的nfc协议，例子为nfca和nfcv
        //在Intent filters里声明你想要处理的Intent，一个tag被检测到时先检查前台发布系统，
        //如果前台Activity符合Intent filter的要求，那么前台的Activity的将处理此Intent。
        //如果不符合，前台发布系统将Intent转到Intent发布系统。如果指定了null的Intent filters，
        //当任意tag被检测到时，你将收到TAG_DISCOVERED intent。因此请注意你应该只处理你想要的Intent。
        techList = new String[][] {
                new String[] { android.nfc.tech.NfcV.class.getName() },
                new String[] { android.nfc.tech.NfcA.class.getName() } };
        intentFilters = new IntentFilter[] { new IntentFilter(
                NfcAdapter.ACTION_TECH_DISCOVERED), };
        //创建一个 PendingIntent 对象, 这样Android系统就能在一个tag被检测到时定位到这个对象
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    public void onNewIntent(Intent intent) {

        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Toast.makeText(this, ""+tag.toString(), Toast.LENGTH_LONG).show();
//        return;
        String data = readTag(tag);
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();

//        Tag tag = intent.getParcelableExtra(mNfcAdapter.EXTRA_TAG);
//        String[] techList = tag.getTechList();
//        boolean haveMifareUltralight = false;
//        for (String tech : techList) {
//            if (tech.indexOf("MifareClassic") >= 0) {
//                haveMifareUltralight = true;
//                break;
//            }
//        }
//        if (!haveMifareUltralight) {
//            Toast.makeText(this, "不支持MifareClassic", Toast.LENGTH_LONG).show();
//            return;
//        }
////        if (mWriteData.isChecked()) {
////            writeTag(tag);
////        } else {
//            String data = readTag(tag);
//            if (data != null) {
//                Log.i(data, "ouput");
//                Toast.makeText(this, data, Toast.LENGTH_LONG).show();
//            }
////        }
    }


    private void read(Tag tag){
//        MifareClassic mc = MifareClassic.get(tag);
//        short startAddress = 0;
//        short endAddress = 5;
//
//        byte[] data = new byte[(endAddress - startAddress + 1 ) * ByteCountPerBlock];
//
//        try {
//            mc.connect();for (short i = startAddress; i <= endAddress; i++ ,time++) {
//                boolean auth = false;
//                short sectorAddress = getSectorAddress(i);
//                auth = mc.authenticateSectorWithKeyA(sectorAddress, MifareClassic.KEY_DEFAULT);
//                if (auth){
//
//                    //the last block of the sector is used for KeyA and KeyB cannot be overwritted
//                    short readAddress = (short)(sectorAddress == 0 ? i : i + sectorAddress);
//
//                    byte[] response = mc.readBlock(readAddress);
//                    CombineByteArray(data, response, time * ByteCountPerBlock);
//                }
//                else{
//                    throw new NfcException(NfcErrorCode.TemporaryError,
//                            "Authorization Error.");
//                }
//            }
//
//            mc.close();
//
//        }
//        catch (NfcException ne) {
//            throw ne;
//        }
//        catch (IOException e) {
//            throw new NfcException(NfcErrorCode.TemporaryError,
//                    "Get response, what it is not successfully.", e);
//        }
//        finally
//        {
//            try {
//                mc.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null) mNfcAdapter.disableForegroundDispatch(this);
    }

    public void writeTag(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);
        try {
            mfc.connect();
            boolean auth = false;
            short sectorAddress = 1;
            auth = mfc.authenticateSectorWithKeyA(sectorAddress, MifareClassic.KEY_NFC_FORUM);
            if (auth) { // the last block of the sector is used for KeyA and KeyB cannot be overwritted
                mfc.writeBlock(4, "1313838438000000".getBytes());
                mfc.writeBlock(5, "1322676888000000".getBytes());
                mfc.close();
                Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) { // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                mfc.close();
            } catch (IOException e) { // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    public String readTag(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);
        for (String tech : tag.getTechList()) {
            System.out.println(tech);
        }
        boolean auth = false; //读取TAG
        try {
            String metaInfo = "";
            //Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();
            //获取TAG的类型
            int sectorCount = mfc.getSectorCount();
            //获取TAG中包含的扇区数
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共" + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n";
            for (int j = 0; j < sectorCount; j++) {
                //Authenticate a sector with key A.
                auth = mfc.authenticateSectorWithKeyA(j, MifareClassic.KEY_NFC_FORUM);
                int bCount;
                int bIndex;
                if (auth) {
                    metaInfo += "Sector " + j + ":验证成功\n";
                    // 读取扇区中的块
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo += "Block " + bIndex + " : " + bytesToHexString(data) + "\n";
                        bIndex++;
                    }
                } else {
                    metaInfo += "Sector " + j + ":验证失败\n";
                }
            }
            return metaInfo;
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (mfc != null) {
                try {
                    mfc.close();
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
        return null;
    }
}
