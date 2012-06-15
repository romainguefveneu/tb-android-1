package net.gordon.tb1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ActivityB extends Activity {

	public static final int RESULT_CODE = 1;

	private EditText valeurEditText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b_layout);

		valeurEditText = (EditText) findViewById(R.id.valeurEditText);

		// OK
		findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Créer l'intent pour les données de retour
				final Intent data = new Intent();
				data.putExtra("value", valeurEditText.getText().toString());

				// Renvoyer le tout
				setResult(RESULT_OK, data);
				finish();
			}
		});

		// Annuler
		findViewById(R.id.annuler).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}