package com.example.tektraining21.demorest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by tektraining21 on 7/21/2014.
 */
public class FlowerAdapter extends ArrayAdapter<Flower> {

    private Context context;
    private List<Flower> flowerList;


    public FlowerAdapter(Context context, int resource, List<Flower> objects) {
        super(context, resource, objects);
        this.context = context;
        this.flowerList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);

        Flower flower = flowerList.get(position);
        TextView textView = (TextView) view.findViewById(R.id.TextView1);
        textView.setText(flower.getName().toString());

        if (flower.getBitmap() != null){
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
            imageView.setImageBitmap(flower.getBitmap());

        }
        else {

            FlowerAndView container = new FlowerAndView();
            container.flower = flower;
            container.view = view;

            ImageLoader loader = new ImageLoader();
            loader.execute(container);

        }



        return view;
    }

    class FlowerAndView {
        public Flower flower;
        public View view;
        public Bitmap bitmap;
    }

    private class ImageLoader extends AsyncTask<FlowerAndView, Void, FlowerAndView> {

        @Override
        protected FlowerAndView doInBackground(FlowerAndView... flowerAndViews) {

            FlowerAndView container = flowerAndViews[0];
            Flower flower = container.flower;

            String imageURL = MainActivity.PHOTO_BASE_URL + flower.getPhoto();
            InputStream stream = null;
            try {
                stream = (InputStream) new URL(imageURL).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                container.bitmap = bitmap;
                return container;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(FlowerAndView flowerAndView) {

            ImageView imageView = (ImageView) flowerAndView.view.findViewById(R.id.imageView1);
            imageView.setImageBitmap(flowerAndView.bitmap);
            flowerAndView.flower.setBitmap(flowerAndView.bitmap);
        }
    }
}
