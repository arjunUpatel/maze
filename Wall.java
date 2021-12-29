import java.awt.Color;
import java.awt.GradientPaint;
public class Wall {
    private int rows[];
    private int cols[];
    private int r;
    private int g;
    private int b;
    private String type;
    private int size;
    public Wall(int rows[], int cols[], int r, int g, int b, String type, int size){
        this.rows=rows;
        this.cols=cols;
        this.r=r;
        this.g=g;
        this.b=b;
        this.type=type;
        this.size=size;
    }
    public int[] getRows() {
        return rows;
    }
    public int[] getCols() {
        return cols;
    }
    public int getR() {
        return r;
    }
    public int getG() {
        return g;
    }
    public int getB() {
        return b;
    }
    public String getType() {
        return type;
    }
    public int getSize() {
        return size;
    }
    public GradientPaint getLeftPaint(int n){
        int endR=255-50*(n+1);
        int endG=255-50*(n+1);
        int endB=255-50*(n+1);
        if(endR<0){
            endR=0;
            endG=0;
            endB=0;
        }
        return new GradientPaint(rows[0],cols[0],new Color(r,g,b),rows[1]+50,cols[0],new Color(endR,endG,endB));
    }

    public GradientPaint getRightPaint(int n){
        int endR=255-50*(n+1);
        int endG=255-50*(n+1);
        int endB=255-50*(n+1);
        if(endR<0){
            endR=0;
            endG=0;
            endB=0;
        }
        return new GradientPaint(rows[0]+50,cols[0],new Color(endR,endG,endB),rows[1]+100,cols[0],new Color(r,g,b));
    }

    public GradientPaint getFloorPaint(int n){
        int endR=255-50*(n+1);
        int endG=255-50*(n+1);
        int endB=255-50*(n+1);
        if(endR<0){
            endR=5;
            endG=5;
            endB=5;
        }
        return new GradientPaint(rows[0],cols[0],new Color(endR,endG,endB),rows[0],cols[1],new Color(r,g,b));
    }

    public GradientPaint getCeilingPaint(int n){
        int endR=255-50*(n+1);
        int endG=255-50*(n+1);
        int endB=255-50*(n+1);
        if(endR<0){
            endR=5;
            endG=5;
            endB=5;
        }

        System.out.println(cols[0]);
        System.out.println(cols[1]);
        return new GradientPaint(rows[0],cols[0],new Color(endR,endG,endB),rows[0],cols[1]-100,new Color(r,g,b));
    }
} 