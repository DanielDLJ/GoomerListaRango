package br.com.danieldlj.goomerlistarango.ViewRestaurant.ViewHolder;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import br.com.danieldlj.goomerlistarango.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class TituloViewHolder extends GroupViewHolder {

    private TextView tituloName;
    private ImageView arrow;

    public TituloViewHolder(View itemView) {
        super(itemView);
        tituloName = (TextView) itemView.findViewById(R.id.group_name);
        arrow = (ImageView) itemView.findViewById(R.id.dropDown);
    }

    public void setGenreTitle(ExpandableGroup genre) {
        tituloName.setText(genre.getTitle());
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
