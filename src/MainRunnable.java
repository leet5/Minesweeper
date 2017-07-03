
public class MainRunnable implements Runnable {
    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted())
            {
                Thread.sleep(1000);
                MainFrame.getPanel().setTime();
                MainFrame.getPanel().revalidate();
                MainFrame.getPanel().repaint();
            }
        }catch (InterruptedException e){
        }
    }
}
