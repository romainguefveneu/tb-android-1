package net.gordon.tb1;

import net.gordon.tb1.bean.Personne;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityD extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d_layout);

		final PersonneAdapter personneAdapter = new PersonneAdapter(this);
		setListAdapter(personneAdapter);

		for (int i = 0; i < 10000; i++) {
			final Personne p = new Personne();
			p.nom = "Nom " + i;
			p.prenom = "Prénom " + i;

			personneAdapter.add(p);
		}

		personneAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final Personne personne = (Personne) l.getItemAtPosition(position);

		Toast.makeText(this, String.format("Nom : %s\nPrénom : %s", personne.nom, personne.prenom), Toast.LENGTH_LONG)
				.show();
	}

	/**
	 * Adapter de la liste de personnes.
	 * 
	 * @author romain
	 */
	private class PersonneAdapter extends ArrayAdapter<Personne> {

		private ViewHolder holder;

		public PersonneAdapter(Context context) {
			super(context, R.layout.item_personne);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout newView;
			final Personne item = getItem(position);

			if (convertView == null) {
				final LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

				newView = new LinearLayout(getContext());
				layoutInflater.inflate(R.layout.item_personne, newView, true);

				holder = new ViewHolder();
				holder.text1 = (TextView) newView.findViewById(android.R.id.text1);
				holder.text2 = (TextView) newView.findViewById(android.R.id.text2);

				newView.setTag(holder);
			} else {
				newView = (LinearLayout) convertView;
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text1.setText(item.prenom);
			holder.text2.setText(item.nom);

			return newView;

		}

		private class ViewHolder {
			TextView text1;
			TextView text2;
		}

	}

}
