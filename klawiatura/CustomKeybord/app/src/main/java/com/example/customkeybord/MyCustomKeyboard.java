package com.example.customkeybord;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MyCustomKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    String nfc = "";
    String bt = "";

    private static final String TAG = "MainActivity";
    StringBuilder messages;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothConnectionService mBluetoothConnection;

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    BluetoothDevice mBTDevice;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();


    @Override
     public void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
        //mBluetoothAdapter.cancelDiscovery();
    }


    @Override
    public View onCreateInputView() {
        KeyboardView keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        Keyboard keyboard = new Keyboard(this, R.xml.number_pad);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }
    public View onCreateInputView2() {

        KeyboardView keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        Keyboard keyboard = new Keyboard(this, R.xml.number_pad2);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }
    public View onCreateInputView3() {

        KeyboardView keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        Keyboard keyboard = new Keyboard(this, R.xml.number_pad3);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }
    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }
    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }

    String isnfc = "";
    String connec = "No Internet Connection";
    @Override
    public void onKey(int primatyCode, int[] keyCodes) {
        final MediaPlayer s = MediaPlayer.create(this,R.raw.sound);
        InputConnection inputConnection = getCurrentInputConnection();

        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        if (inputConnection != null) {
            switch(primatyCode) {
                case 1:
                    s.start(); //Wyświetlenie przykładowego tekstu
                    CharSequence selectedTextt = ic.getSelectedText(0);
                    ic.commitText("Przykładowy tekst", 1);
                    break;
                case 2:
                    s.start(); //Odtworzenie dźwięku
                    final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
                    mp.start();
                    break;
                case 3:
                    s.start(); //Uruchomienie aparatu
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                    break;
                case 4:
                    s.start();
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null) {
                        bt = "BT don't supported!";
                    } else if (!mBluetoothAdapter.isEnabled()) {
                        bt = "BT disabled!";
                    } else {
                        bt = "BT enabled!";
                    }
//                Toast.makeText(this, bt, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(getApplicationContext(), "Tu będzie odbieranie", Toast.LENGTH_SHORT).show();
                    startConnection();
                    break;
                case 5:
                    s.start(); //Przykładowy toast
                    Toast.makeText(MyCustomKeyboard.this,"Przykładowy toast", Toast.LENGTH_SHORT).show();

                    break;
                case 6:
                    s.start(); //Zmiana układu klawiatury
                    setInputView(onCreateInputView2());
                    break;
                case 7:
                    s.start(); //Sprawdzenie czy NFC jest uruchomione
                    NfcManager manager = (NfcManager) getApplicationContext().getSystemService(Context.NFC_SERVICE);
                    NfcAdapter adapter = manager.getDefaultAdapter();

                    if (adapter != null && adapter.isEnabled()){
                        isnfc = "NFC jest włączone";
                    }   else {
                        isnfc = "NFC jest wyłączone";
                    }
                    toastMsg(isnfc);

                    break;
                case 8:
                    s.start(); //Wejście w ustawienia NFC
                    Intent intent2 = new Intent(Settings.ACTION_NFC_SETTINGS);
                    startActivity(intent2);
                    break;
                case 9:
                    s.start(); //Uruchomienie przeglądarki google
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                    startActivity(browserIntent);
                    break;
                case 10:
                    s.start(); //Uruchomienie kalendarza
                    Intent i=getPackageManager().getLaunchIntentForPackage("com.android.calendar");
                    startActivity(i);
                    break;
                case 11:
                    s.start(); //Zmiana układu klawiatury
                    setInputView(onCreateInputView());
                    break;
                case 12:
                    s.start(); //Zmiana układu klawiatury
                    setInputView(onCreateInputView3());
                    break;
                case 13:
                    s.start(); //Zmiana układu klawiatury
                    setInputView(onCreateInputView2());
                    break;
                case 14:
                    s.start();
                    break;
                case 15:
                    s.start();
                    break;
                case 16:
                    s.start();
                    break;
                default :
                    char code = (char) primatyCode;
                    inputConnection.commitText(String.valueOf(code), 1);
            }
        }
    }

//Odbieranie i wyświetlanie tekstu z aplikacji wysyłającej
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theMessage");
            messages.setLength(0);
            messages.append(text + "\n");
            //Wyświetlenie tekstu w polu wpisywania
            InputConnection ic = getCurrentInputConnection();
            ic.commitText(messages, 1);

        }
    };

    public void startConnection(){
        messages = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("incomingMessage"));
        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                if(deviceName.equals("Z2131")) {
                    mBTDevice = device;
                    startBTConnection(mBTDevice,MY_UUID_INSECURE);
                }
            }
        }

    }

    /**
     * starting chat service method
     */
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection = new BluetoothConnectionService(MyCustomKeyboard.this);

        mBluetoothConnection.startClient(device,uuid);
    }

    private void playClick(int i) {

        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(i)
        {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}