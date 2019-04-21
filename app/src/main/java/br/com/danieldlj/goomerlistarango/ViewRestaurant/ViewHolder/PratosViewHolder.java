package br.com.danieldlj.goomerlistarango.ViewRestaurant.ViewHolder;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.danieldlj.goomerlistarango.Model.HourModel;
import br.com.danieldlj.goomerlistarango.Model.OfertasModel;
import br.com.danieldlj.goomerlistarango.R;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.Models.PratoModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class PratosViewHolder extends ChildViewHolder {

    private String Tag = "PratosViewHolder";
    private ImageView prato_photo;
    private TextView prato_name,prato_description,prato_valor,prato_valor_oferta;

    public PratosViewHolder(View itemView) {
        super(itemView);
        prato_photo = (ImageView) itemView.findViewById(R.id.prato_photo);
        prato_name = (TextView) itemView.findViewById(R.id.prato_name);
        prato_description = (TextView) itemView.findViewById(R.id.prato_description);
        prato_valor = (TextView) itemView.findViewById(R.id.prato_valor);
        prato_valor_oferta = (TextView) itemView.findViewById(R.id.prato_valor_oferta);
    }

    public void setPratoName(String name) {
        prato_name.setText(name);
    }

    public void setValor(String valor, ArrayList<OfertasModel> sales,String nome) {

        if(valor.equals("null")){
            prato_valor.setText("R$ -");
            prato_valor_oferta.setText("");
            return;
        }

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int NowWeek = calendar.get(Calendar.DAY_OF_WEEK);

        for(OfertasModel oferta: sales){

            for(HourModel dias : oferta.getHours()){

                for (Integer weekSale: dias.getDays()){
                    if(NowWeek == weekSale ){
                        try {

                            SimpleDateFormat simpleDateformat = new SimpleDateFormat("HH:mm");

                            Date time1 = simpleDateformat.parse(dias.getFrom());
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.setTime(time1);
                            calendar1.add(Calendar.WEEK_OF_MONTH, weekSale);

                            Date time2 = simpleDateformat.parse(dias.getTo());
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.setTime(time2);

                            Date d = simpleDateformat.parse(simpleDateformat.format(now));
                            Calendar calendar3 = Calendar.getInstance();
                            calendar3.setTime(d);
                            calendar3.add(Calendar.WEEK_OF_MONTH, NowWeek);



                            if(time1.after(calendar2.getTime())){
                                Log.d(Tag,"Tem horario depois");
                                if(weekSale == 7 ){
                                    calendar2.add(Calendar.WEEK_OF_MONTH, 1);
                                }else {
                                    calendar2.add(Calendar.WEEK_OF_MONTH, weekSale +1);
                                }
                            }else{
                                Log.d(Tag,"Nao tem horario depois");
                                calendar2.add(Calendar.WEEK_OF_MONTH, weekSale);
                            }

                            Date x = calendar3.getTime();
                            if ((x.after(calendar1.getTime()) && x.before(calendar2.getTime()))|| x.equals(calendar2.getTime())|| x.equals(calendar1.getTime())) {
                                Log.d(Tag,"Entrou 5");
                                prato_valor.setText("R$ "+oferta.getPrice());

                                prato_valor_oferta.setPaintFlags(prato_valor_oferta.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                prato_valor_oferta.setText("R$ "+valor);
                                return;
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }

        prato_valor.setText("R$ "+valor);
        prato_valor_oferta.setText("");
    }
    public void setPhoto(String image) {
        Picasso.get().load(image).placeholder(R.drawable.restaurant_photo_default).into(prato_photo);
    }


    public void openDialog(PratoModel pratoModel, Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.view_prato_dialog);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final float[] valor_prato_float = {0};
        final float[] valor_total_float = {0};

        CircleImageView close_dialog = (CircleImageView) dialog.findViewById(R.id.close_dialog);
        ImageView ptrato_photo_dialog = (ImageView) dialog.findViewById(R.id.ptrato_photo);
        TextView prato_nome_dialog = (TextView) dialog.findViewById(R.id.prato_nome);
        TextView prato_description_dialog = (TextView) dialog.findViewById(R.id.prato_description);
        TextView prato_valor_dialog = (TextView) dialog.findViewById(R.id.prato_valor);
        TextView menos_btn = (TextView) dialog.findViewById(R.id.menos_btn);
        final TextView contador = (TextView) dialog.findViewById(R.id.contador);
        TextView mais_btn = (TextView) dialog.findViewById(R.id.mais_btn);
        final TextView valor_total = (TextView) dialog.findViewById(R.id.valor_total);


        Picasso.get().load(pratoModel.getImage()).placeholder(R.drawable.restaurant_photo_default).into(ptrato_photo_dialog);


        prato_nome_dialog.setText(pratoModel.getName());
        if(prato_valor.getText().toString().equals("R$ -")){
            prato_valor_dialog.setText("0");
            valor_prato_float[0] = 0;
        }else{
            if(prato_valor_oferta.getText().toString().equals("")){
                prato_valor_dialog.setText(prato_valor.getText().toString());
                valor_prato_float[0] = pratoModel.getPrice();
            }else {
                prato_valor_dialog.setText(prato_valor.getText().toString());
                String valorAux = prato_valor.getText().toString();
                valorAux = valorAux.substring(valorAux.indexOf(" "),valorAux.length());
                valor_prato_float[0] = Float.parseFloat(valorAux);
            }
        }

        valor_total.setText(String.valueOf(valor_prato_float[0]));

        valor_total_float[0] = valor_prato_float[0];
        menos_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cont = Integer.parseInt(contador.getText().toString());
                if(cont != 0){
                    cont = cont - 1;
                    contador.setText(String.valueOf(cont));
                    valor_total_float[0] = valor_prato_float[0] *cont;
                    valor_total.setText(String.valueOf(valor_total_float[0]));
                }
            }
        });

        mais_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cont = Integer.parseInt(contador.getText().toString());

                cont = cont + 1;
                contador.setText(String.valueOf(cont));
                valor_total_float[0] = valor_prato_float[0] * cont;
                valor_total.setText(String.valueOf(valor_total_float[0]));
            }
        });


        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
