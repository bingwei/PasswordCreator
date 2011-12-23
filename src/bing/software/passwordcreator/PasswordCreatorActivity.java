package bing.software.passwordcreator;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordCreatorActivity extends Activity {
	private final String TAG = "passwordcreator";
	private EditText etPlaintextPassword;
	private EditText etPasswordDigit;
	private Button btnGenerate;
	private Button btnAgain;
	private Button btnShow;
	private TextView tvPlaintext;
	private TextView tvDecimal;
	private TextView tvMd5;
	private TextView tvWarning;
	private CheckBox cbHide;
	private String passwordDecimal = "";
	private String passwordMd5 = "";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        etPlaintextPassword = (EditText)findViewById(R.id.etPlaintextPassword);
        etPasswordDigit = (EditText)findViewById(R.id.etPasswordDigit);
        btnGenerate = (Button)findViewById(R.id.btnGenerate);
        btnAgain = (Button)findViewById(R.id.btnAgain);
        btnShow = (Button)findViewById(R.id.btnShow);
        cbHide = (CheckBox)findViewById(R.id.cbHide);
        tvPlaintext = (TextView)findViewById(R.id.tvPlaintext);
        tvDecimal = (TextView)findViewById(R.id.tvDecimal);
        tvMd5 = (TextView)findViewById(R.id.tvMd5);
        tvWarning = (TextView)findViewById(R.id.tvWarning);
        
        buttonMonitor();
        
        btnGenerate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getPassword(etPlaintextPassword.getText().toString());
				buttonMonitor();
			}
		});
        btnAgain.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
				getPassword(tvMd5.getText().toString());
        	}
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		boolean isDigitFilled = etPasswordDigit.getText().length() == 0? false: true;
        		int digit = isDigitFilled?
        				Integer.parseInt(etPasswordDigit.getText().toString()):
        				Integer.parseInt(etPasswordDigit.getHint().toString());
        		if(digit < passwordDecimal.length()
        				&& digit < passwordMd5.length()){
        			getPasswordWithDigit(digit);
        			tvWarning.setText("");
        		}else{
        			tvWarning.setText(getString(R.string.digitIsTooBig));
        		}
        	}
        });
        
        cbHide.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					tvPlaintext.setVisibility(View.INVISIBLE);
					tvDecimal.setVisibility(View.INVISIBLE);
					tvMd5.setVisibility(View.INVISIBLE);
				}else{
					tvPlaintext.setVisibility(View.VISIBLE);
					tvDecimal.setVisibility(View.VISIBLE);
					tvMd5.setVisibility(View.VISIBLE);
				}
			}
        	
        });
    }
    
    private void buttonMonitor(){
    	if(passwordMd5.length() != 0){
    		btnShow.setEnabled(true);
    	}else{
    		btnShow.setEnabled(false);
    	}
    }
    private void getPassword(String plaintextPassword){
    	try {
			passwordMd5 = Utils.getMD5(plaintextPassword);
			passwordDecimal = Utils.getDecimal(passwordMd5);
			tvPlaintext.setText(plaintextPassword);
			tvMd5.setText(passwordMd5);
			tvDecimal.setText(passwordDecimal);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.toString());
		}
    }
    private void getPasswordWithDigit(int passwordDigit){
		tvDecimal.setText(passwordDecimal.substring(0, passwordDigit));
		tvMd5.setText(passwordMd5.substring(0, passwordDigit));
    }
}