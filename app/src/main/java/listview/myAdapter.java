package listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class myAdapter extends ArrayAdapter {
     private final int ImageId;

    public myAdapter(Context context, int headImage, List<myBean> obj) {
        super( context, headImage, obj );
        this.ImageId = headImage;
    }

//        @androidx.annotation.NonNull
//        @Override
//        public View getView(int position, @androidx.annotation.Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {
//            return super.getView( position, convertView, parent );
//        }
}
