class Cell {
    static final int SCALE = 32;
    private boolean bee = false;
    private boolean revealed = false;
    private boolean flag = false;
    private int i;
    private int j;
    private int x;
    private int y;

    Cell(int i, int j) {
        if (Math.random() < 0.1)
            bee = false;
        this.i = i;
        this.j = j;

        this.x = i * SCALE;
        this.y = j * SCALE;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    boolean isBee() {
        return bee;
    }

    boolean isRevealed() {
        return revealed;
    }

    public void changeReveal() {
        revealed = true;
        if (this.getNeighbors() == 0)
            floodFill();
    }

    public void setFlag() {
        if(!revealed)
        flag = !flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setBee(boolean bee) {
        this.bee = bee;
    }

    @SuppressWarnings("Duplicates")
    public int getNeighbors() {
        if (this.bee)
            return -1;

        int total = 0;
        for (int xoff = -1; xoff <= 1; xoff++) {
            for (int yoff = -1; yoff <= 1; yoff++) {
                int i = this.i + xoff;
                int j = this.j + yoff;
                if (i > -1 && i < Canvas.getCellsX() && j > -1 && j < Canvas.getCellsY()) {
                    Cell c = Canvas.getGrid()[i][j];
                    if (c.isBee())
                        total++;
                }
            }
        }
        return total;
    }

    @SuppressWarnings("Duplicates")
    public void floodFill() {
        for (int xoff = -1; xoff <= 1; xoff++) {
            for (int yoff = -1; yoff <= 1; yoff++) {
                int i = this.i + xoff;
                int j = this.j + yoff;
                if (i > -1 && i < Canvas.getCellsX() && j > -1 && j < Canvas.getCellsY()) {
                    Cell c = Canvas.getGrid()[i][j];
                    if (!c.isBee() && !c.isRevealed())
                        c.changeReveal();
                }
            }
        }
    }
}
