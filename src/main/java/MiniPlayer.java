package main.java;


import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniPlayer {

    static JFrame f;
    static MyPanel control;

    public void go(){
        f = new JFrame("Music plus Graphics");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400, 400);
        control = new MyPanel();
        f.getContentPane().add(control);
        f.setVisible(true);
    }

    public static void main(String[] args)
    {
        MiniPlayer pl = new MiniPlayer();
        pl.go();
        pl.StartAlg();
    }

    public void StartAlg()
    {
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(control,eventsIWant);

            Sequence seq = new Sequence(Sequence.PPQ,4);
            Track track = seq.createTrack();
        for (int i = 5; i<61; i+=4)
        {
            track.add(makeEvent(144,1,i,100,i));
            track.add(makeEvent(176,1,127,0,i));
            track.add(makeEvent(128,1,i,100,i+2));
        }
        sequencer.setSequence(seq);
        sequencer.setTempoInBPM(220);
        sequencer.start();
        } catch (Exception ex){ex.printStackTrace();}
    }

    public static MidiEvent makeEvent (int comd, int chan, int one, int two, int tick)
    {
        MidiEvent event = null;
                try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a,tick);
                } catch (Exception e) {}
                return event;
    }

    /*
    static class  Control implements ControllerEventListener {
        public void controlChange(ShortMessage event)
        {
            System.out.println(" LA ");
        }
    }
    */
         class MyPanel extends JPanel implements ControllerEventListener{
             boolean msg = false;
            public void controlChange(ShortMessage event)
            {
                System.out.println(" LA ");
                msg = true;
                control.repaint();
            }
            public void PaintComponent(Graphics g)
            {
                if (msg)
                {
                    Graphics2D g2 = (Graphics2D) g;
                int x = (int)(/*Math.random()*50 +*/ 100);
                int y = (int)(/*Math.random()*50 +*/ 100);
                g2.setColor(Color.GREEN);
                g2.fillOval(x,y,10,10);
                msg = false;
                }
            }

        }

}