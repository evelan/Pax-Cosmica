package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyButton extends Actor {
    private Button button;


    public MyButton(String file){
    	button = new Button(file);
    }
    
    public MyButton(float x, float y, String file){
    	button = new Button(x,y,file);
    }
    public MyButton(float x, float y, float z, float v, String file){
    	button = new Button(x,y,z,v,file);
    }
    public MyButton(float x, float y, float z, String file){
    	button = new Button (x,y,z,file);
    }
	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}
	@Override
    public void draw(Batch batch, float alpha){
        batch.draw(this.button.getTexture(),this.button.getX(),this.button.getY(), this.button.getWidth(), this.button.getHeight());
    }
}