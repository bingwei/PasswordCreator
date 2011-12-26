package bing.software.passwordcreator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordCreatorActivity extends Activity {
	private EditText etPlaintextPassword;
	private EditText etPasswordDigit;
	private Button btnGenerate;
	private Button btnAgain;
	private Button btnShow;
	private Button btnRefresh;
	private TextView tvPlaintext;
	private TextView tvDecimal;
	private TextView tvMd5;
	private TextView tvWarning;
	private CheckBox cbHide;
	private String passwordDecimal = "";
	private String passwordMD5 = "";
	private final int DIALOG_ABOUT_INFO = 0;
	private final int DIALOG_HELP_INFO = 1;
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
        btnRefresh = (Button)findViewById(R.id.btnRefresh);
        cbHide = (CheckBox)findViewById(R.id.cbHide);
        tvPlaintext = (TextView)findViewById(R.id.tvPlaintext);
        tvDecimal = (TextView)findViewById(R.id.tvDecimal);
        tvMd5 = (TextView)findViewById(R.id.tvMd5);
        tvWarning = (TextView)findViewById(R.id.tvWarning);
        
        componentsMonitor();
        
        btnGenerate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getPassword(etPlaintextPassword.getText().toString());
				componentsMonitor();
			}
		});
        
        btnAgain.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
				getPassword(tvMd5.getText().toString());
				componentsMonitor();
        	}
        });
        
        btnShow.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		boolean isDigitFilled = etPasswordDigit.getText().length() == 0? false: true;
        		int digit = isDigitFilled?
        				Integer.parseInt(etPasswordDigit.getText().toString()):
        				Integer.parseInt(etPasswordDigit.getHint().toString());
        		if(digit <= passwordDecimal.length()){
        			getPasswordWithDigit(digit);
        			tvWarning.setText("");
        		}else{
        			getPasswordWithDigit(passwordDecimal.length());
        			etPasswordDigit.setText(String.valueOf(passwordDecimal.length()));
        			tvWarning.setText(getString(R.string.warning_digitIsTooBig));
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
        
        btnRefresh.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				passwordDecimal = "";
				passwordMD5 = "";
				etPlaintextPassword.setText("");
				etPasswordDigit.setText("");
				tvPlaintext.setText("");
				tvDecimal.setText("");
				tvMd5.setText("");
				tvWarning.setText("");
				cbHide.setChecked(false);
				componentsMonitor();
			}
        });
    }
    
    @Override
    protected Dialog onCreateDialog(int id){
    	switch(id){
    	case DIALOG_ABOUT_INFO:
    		return new AlertDialog.Builder(PasswordCreatorActivity.this)
    		.setTitle(R.string.about)
    		.setMessage(R.string.aboutMsg)
    		.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int whichButton) {
    			}
    		}).create();
    	case DIALOG_HELP_INFO:
    		return new AlertDialog.Builder(PasswordCreatorActivity.this)
    		.setTitle(R.string.help)
    		.setMessage(R.string.helpMsg)
    		.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int whichButton) {
    			}
    		}).create();
    	default:
    		return null;
    	}
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	  switch (item.getItemId()) {
	  case R.id.about:
		  showDialog(DIALOG_ABOUT_INFO);
		  return true;
	  case R.id.help:
		  showDialog(DIALOG_HELP_INFO);
		  return true;
	  default:
	    return super.onContextItemSelected(item);
	  }
	}
	
    
    private void componentsMonitor(){
    	if(passwordMD5.length() != 0){
    		btnShow.setEnabled(true);
    		cbHide.setEnabled(true);
    	}else{
    		btnShow.setEnabled(false);
    		cbHide.setEnabled(false);
    	}
    }
    
    private void getPassword(String plaintextPassword){
		passwordMD5 = Utils.getMD5(plaintextPassword);
		passwordDecimal = Utils.getDecimal(passwordMD5);
		tvPlaintext.setText(plaintextPassword);
		tvMd5.setText(passwordMD5);
		tvDecimal.setText(passwordDecimal);
    }
    
    private void getPasswordWithDigit(int passwordDigit){
		tvDecimal.setText(passwordDecimal.substring(0, passwordDigit));
		tvMd5.setText(passwordMD5.substring(0, passwordDigit));
    }
}