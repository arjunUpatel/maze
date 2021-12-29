import java.awt.Color;
public class Explorer {
    private Location loc;
    private int dir;
    private int size;
    private Color color;
    public Explorer(Location loc,int dir,int size,Color color){
        this.loc=loc;
        this.dir=dir;
        this.size=size;
        this.color=color;
    }
    public void move(int key, char[][] maze){
        int row=loc.getRow();
        int col=loc.getCol();
        if(key==38){ //forward
            if(dir==0){ //up
                if(loc.getRow()-1>0&&maze[loc.getRow()-1][loc.getCol()]==' '||loc.getRow()-1>0&&maze[loc.getRow()-1][loc.getCol()]=='s'||loc.getRow()-1>0&&maze[loc.getRow()-1][loc.getCol()]=='e'||loc.getRow()-1>0&&maze[loc.getRow()-1][loc.getCol()]=='z'||loc.getRow()-1>0&&maze[loc.getRow()-1][loc.getCol()]=='x')
                    loc.setRow(row-1); 
            }
            if(dir==1){ //right
                if(loc.getCol()+1<=maze[0].length&&maze[loc.getRow()][loc.getCol()+1]==' '||loc.getCol()+1<=maze[0].length&&maze[loc.getRow()][loc.getCol()+1]=='s'||loc.getCol()+1<=maze[0].length&&maze[loc.getRow()][loc.getCol()+1]=='e'||loc.getCol()+1<=maze[0].length&&maze[loc.getRow()][loc.getCol()+1]=='z'||loc.getCol()+1<=maze[0].length&&maze[loc.getRow()][loc.getCol()+1]=='x')
                    loc.setCol(col+1); 
            }
            if(dir==2){ //down
                if(loc.getRow()+1<maze.length&&maze[loc.getRow()+1][loc.getCol()]==' '||loc.getRow()+1<maze.length&&maze[loc.getRow()+1][loc.getCol()]=='s'||loc.getRow()+1<maze.length&&maze[loc.getRow()+1][loc.getCol()]=='e'||loc.getRow()+1<maze.length&&maze[loc.getRow()+1][loc.getCol()]=='z'||loc.getRow()+1<maze.length&&maze[loc.getRow()+1][loc.getCol()]=='s'||loc.getRow()+1<maze.length&&maze[loc.getRow()+1][loc.getCol()]=='e'||loc.getRow()+1<maze.length&&maze[loc.getRow()+1][loc.getCol()]=='x')
                    loc.setRow(row+1); 
            }
            if(dir==3){ //left
                if(loc.getCol()-1<=maze[0].length&&maze[loc.getRow()][loc.getCol()-1]==' '||loc.getCol()-1<=maze[0].length&&maze[loc.getRow()][loc.getCol()-1]=='s'||loc.getCol()-1<=maze[0].length&&maze[loc.getRow()][loc.getCol()-1]=='e'||loc.getCol()-1<=maze[0].length&&maze[loc.getRow()][loc.getCol()-1]=='z'||loc.getCol()-1<=maze[0].length&&maze[loc.getRow()][loc.getCol()-1]=='x')
                    loc.setCol(col-1); 
            }
        }
    }
    public void turnRight(){
        dir++;
        if(dir>3)
            dir=0;
    }
    public void turnLeft(){
        dir--;
        if(dir<0)
            dir=3;
    }
    public Location getLocation() {return loc;}
    public int getDirection() {return dir;}
    public int getSize() {return size;}
    public Color getColor() {return color;}
    public void setDirection(int dir){ this.dir=dir; }
    
    public void setLoc(Location loc) {
        this.loc = loc;
    }
}