package Warriors91I.Utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public enum Sounds {
    FIRE("audios/NotImportant.wav"),
    MAIN("audios/WarriorsMusic.wav"),
    JUMP("audios/jump.wav"),
    WALK("audios/walk_1.wav"),
    ATTACK("audios/attack1.wav"),
    WEAPON("audios/weapon.wav"),

    MURLOC("audios/die.wav");

    private final String path;

    Sounds(String path) {
        this.path = path;
    }

    public void play() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    getClass().getClassLoader().getResourceAsStream(path)
            );
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playLoop() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    getClass().getClassLoader().getResourceAsStream(path)
            );
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
