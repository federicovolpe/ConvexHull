package basic;

public record Facet (Node3D n1, Node3D n2, Node3D n3) {
    /**
     * matematic formula to get the plane equation identified
     * from the tree nodes
     */
    public int[] getPlaneEquation (){
        return new int[]{
            (n2.getY() - n1.getY())*(n3.getZ() - n1.getZ()) - (n2.getZ() - n1.getZ())*(n3.getY() - n1.getY()),
            -((n2.getX()-n1.getX()) * (n3.getZ()-n1.getZ()) - (n2.getZ()-n2.getZ())*(n3.getX()-n1.getX())),
            (n2.getX()-n1.getX())*(n3.getY()-n1.getY()) - (n2.getY()-n1.getY())*(n3.getX()-n1.getX())};
        }   
}
