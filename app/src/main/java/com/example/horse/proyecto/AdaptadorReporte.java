package com.example.horse.proyecto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by horse on 10/04/16.
 */
public class AdaptadorReporte extends ArrayAdapter<Reporte> {

    private Activity context;
    private List<Reporte> reports;

    public AdaptadorReporte(Activity context, List<Reporte> reportes) {
        super(context, R.layout.report_style, reportes);
        this.context = context;
        this.reports = reportes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.report_style, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.header);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.cuerpo);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imagen);

        textViewName.setText("Tipo de reporte: "+reports.get(position).getTipo());
        textViewDesc.setText("Descripcion: "+reports.get(position).getDescripcion() + "\n"
                + "Ubicacion: "+reports.get(position).getUbicacion() + "\n" + "Fecha: "+reports.get(position).getFecha());
        if(reports.get(position).getTipo().equalsIgnoreCase("seguridad"))
            image.setImageResource(android.R.drawable.ic_lock_idle_lock);
        else if(reports.get(position).getTipo().equalsIgnoreCase("luz"))
            image.setImageResource(android.R.drawable.ic_lock_idle_low_battery);
        else
            image.setImageResource(android.R.drawable.ic_dialog_alert);

        return  listViewItem;
    }
}
