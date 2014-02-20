package com.ideone.sample;

import org.w3c.dom.Text;

import com.ideone.sample.RunThread;
import com.ideone.sample.adapters.AdapterLanguages;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class IdeoneSample extends Activity {

	ProgressDialog progressDialog;

	CheckedTextView textOutput;
	CheckedTextView textResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// progress dialog
		progressDialog = ProgressDialog.show(this, "",
				"Loading. Please wait...", false);
		progressDialog.hide();

		final Spinner spinnerLanguage = (Spinner) this
				.findViewById(R.id.spinnerLanguage);
		final EditText edittextSource = (EditText) this
				.findViewById(R.id.edittextSource);
		final EditText edittextInput = (EditText) this
				.findViewById(R.id.edittextInput);
		final TextView stdinText = (TextView) this
				.findViewById(R.id.checkedTextView3);
		final ImageView expandImage = (ImageView) this
				.findViewById(R.id.imagecollaspe1);
		final ImageView expandImage2 = (ImageView) this
				.findViewById(R.id.imagecollaspe2);
		final Activity activity = this;
		edittextInput.setVisibility(View.GONE);
		textOutput = (CheckedTextView) this.findViewById(R.id.textOutput);
		textResult = (CheckedTextView) this.findViewById(R.id.textResult);

		// languages
		spinnerLanguage.setAdapter(new AdapterLanguages(this, 1));

		// execute button
		Button btnExecute = (Button) this.findViewById(R.id.btnExecute);
		btnExecute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				Ideone ideone = new Ideone(activity);

				try {
					RunThread runThread = new RunThread(activity, handler);
					String _s = edittextSource.getText().toString();
					runThread.setSource(_s);
					runThread.setInput(edittextInput.getText().toString());
					runThread.setLang(ideone
							.getLanguageIdByName(spinnerLanguage
									.getSelectedItem().toString()));
					runThread.start();
				} catch (Exception e) {
					AlertDialog.Builder errb = new AlertDialog.Builder(activity);
					errb.setMessage("Error: " + e.toString());
					AlertDialog ad = errb.create();
					ad.show();
				}
			}
		});
		expandImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("Visibility",
						String.valueOf(edittextInput.getVisibility()));
				if (!(edittextInput.getVisibility() == View.VISIBLE))
					edittextInput.setVisibility(View.VISIBLE);
				else
					edittextInput.setVisibility(View.GONE);
			}
		});

		expandImage2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("Visibility",
						String.valueOf(edittextInput.getVisibility()));
				if (!(edittextInput.getVisibility() == View.VISIBLE))
					edittextInput.setVisibility(View.VISIBLE);
				else
					edittextInput.setVisibility(View.GONE);
			}
		});

	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				String command = msg.getData().getString("command");
				String text = msg.getData().getString("text");

				if (command.equals("open")) {
					progressDialog.show();
					textResult.setText("");

				} else if (command.equals("close")) {
					progressDialog.hide();

				} else if (command.equals("echo")) {
					progressDialog.setMessage(text);

				} else if (command.equals("echo2")) {
					textOutput.setText(text);

				} else if (command.equals("result")) {
					textResult.setText(text);

					// Toast.makeText(getApplicationContext(),
					// "Authorization Error", Toast.LENGTH_LONG).show();
				} else if (command.equals("error")) {
					textOutput.setText(text);
					progressDialog.hide();
				}
			} catch (Exception e) {
				// int a = 0;
			}
		}
	};
}