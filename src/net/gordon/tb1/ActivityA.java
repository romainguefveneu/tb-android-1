package net.gordon.tb1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ActivityA extends Activity {
	/**
	 * Boutons.
	 */
	private Button btnIntentA;
	private Button btnIntentB;
	private Button btnIntentC;
	private Button btnThreadD;
	private Button btnListeE;

	/**
	 * Loading task.
	 */
	private Loading loadingTask = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a_layout);

		btnIntentA = (Button) findViewById(R.id.btnA);
		btnIntentB = (Button) findViewById(R.id.btnB);
		btnIntentC = (Button) findViewById(R.id.btnC);
		btnThreadD = (Button) findViewById(R.id.btnD);
		btnListeE = (Button) findViewById(R.id.btnE);

		// Afficher l'Activity C
		btnIntentA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(ActivityA.this, ActivityC.class);
				ActivityA.this.startActivity(intent);
			}
		});

		// Afficher l'Activity B
		btnIntentB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(ActivityA.this, ActivityB.class);
				ActivityA.this.startActivityForResult(intent, ActivityB.RESULT_CODE);
			}
		});

		// Déclencher la fenêtre d'appel d'un numéro
		btnIntentC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Uri telUri = Uri.parse("tel:36656565");
				final Intent intent = new Intent(Intent.ACTION_VIEW, telUri);

				startActivity(intent);
			}
		});

		// Afficher une dialogue de chargement
		btnThreadD.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (loadingTask == null || !loadingTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
					loadingTask = (Loading) new Loading().execute();
				}
			}
		});
		
		// Afficher l'Activity D
		btnListeE.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(ActivityA.this, ActivityD.class);
				ActivityA.this.startActivity(intent);
			}
		});
	}

	/**
	 * Méthode gérant les retours suite à un startActivityForResult().
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ActivityB.RESULT_CODE) {
			if (resultCode == RESULT_OK) {
				final String value = data.getStringExtra("value");
				Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), getString(R.string.aucuneValeur), Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * Classe simulant un long chargement.
	 * 
	 * @author romain
	 */
	private class Loading extends AsyncTask<Void, Integer, Void> {

		/**
		 * Boite de dialogue
		 */
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(ActivityA.this);
			progressDialog.setTitle("Chargement");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			for (int i = 0; i < 101; i++) {
				publishProgress(i);
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// Tant pis
				}
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

			progressDialog.setProgress(values[0]);
			// Ou alors :
			// progressDialog.incrementProgressBy(1);
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			progressDialog.dismiss();
		}

	}

}