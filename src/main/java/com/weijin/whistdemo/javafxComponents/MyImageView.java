package com.weijin.whistdemo.javafxComponents;

import com.weijin.whistdemo.utils.helper;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class MyImageView extends ImageView implements Cloneable, Serializable {
    private static final long serialVersionUID = 5794148504376232369L;

    public MyImageView() {
        super();
    }

    public MyImageView(String s) {
        super(s);
    }

    public MyImageView(Image image) {
        super(image);
    }

//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        MyImageView myImageView = (MyImageView) super.clone();
//        myImageView.imageProperty().set(this.imageProperty().get());
//        myImageView.fitHeightProperty().set(this.fitHeightProperty().get());
//        myImageView.fitWidthProperty().set(this.fitWidthProperty().get());
//        myImageView.xProperty().set(this.xProperty().get());
//        myImageView.yProperty().set(this.yProperty().get());
//        myImageView.translateXProperty().set(this.translateXProperty().get());
//        myImageView.translateYProperty().set(this.translateYProperty().get());
//        return myImageView;
//    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        MyImageView myImageView = helper.SerialCloneUtils.deepClone(this);
        myImageView.imageProperty().set(this.imageProperty().get());
        myImageView.fitHeightProperty().set(this.fitHeightProperty().get());
        myImageView.fitWidthProperty().set(this.fitWidthProperty().get());
        myImageView.xProperty().set(this.xProperty().get());
        myImageView.yProperty().set(this.yProperty().get());
        myImageView.translateXProperty().set(this.translateXProperty().get());
        myImageView.translateYProperty().set(this.translateYProperty().get());
        return myImageView;
    }
}
