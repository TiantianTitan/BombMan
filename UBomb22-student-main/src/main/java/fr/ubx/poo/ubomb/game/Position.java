package fr.ubx.poo.ubomb.game;

public record Position (double x, double y) {

    public Position(Position position) {
        this(position.x, position.y);
    }

    public boolean inCase(Position casePos){
        if(this.x >= casePos.x -0.5 && this.x <= casePos.x + 0.5 && this.x >= casePos.x -0.5 && this.x <= casePos.x + 0.5) {
            return true;
        } 
        return false;
    }

    public Position centerPosition(){
        return new Position((int)this.x,(int)this.y);
    }

}
