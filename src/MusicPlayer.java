import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Class MusicPlayer - This class is used to play music when needed.
 * When the player enters the same room as the Bad Boy Chiller Crew, their music is played.
 * The code was inspired by Max O'Didily, https://www.youtube.com/watch?v=TErboGLHZGA
 *
 * @author Max O'Didily, Valentin Magis, K20076746
 * @version 24.11.2020
 */
public class MusicPlayer
{
    Clip clip;
    boolean isPlaying = false;

    /**
     * Play the music with the specified name.
     * @param musicLocation The name of the music file / the filepath.
     */
    public void playMusic(String musicLocation){
        try {
            File musicPath = new File(musicLocation);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                isPlaying = true;
            }
        }

        catch (Exception e){

        }
    }

    /**
     * Stop playing the music
     */
    public void stopMusic(){
        clip.stop();
        isPlaying = false;
    }

    /**
     * Get if the music is playing
     * @return Boolean if the music is playing
     */
    public boolean isPlaying()
    {
        return isPlaying;
    }
    }