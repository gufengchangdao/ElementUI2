package com.element.util;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 提示音工具类
 */
public class AudioUtil {
	/**
	 * 对组件事件播放自定义提示音，如果播放失败会使用系统默认提示音
	 * <p>
	 * 如果事件已有音频，比如打开对话框的提示音，可以使用下面的代码全局关闭:
	 * <pre>
	 * UIManager.put(AUDITORY_KEY, UIManager.get("AuditoryCues.noAuditoryCues"));
	 * </pre>
	 *
	 * @param p             发生事件的组件
	 * @param audioResource 音频文件输入流，音频时长不宜过长，等音频播放完才继续执行
	 * @param task          音频开始播放时执行的任务
	 * @throws RuntimeException 播放音频失败
	 */
	public static void playAudio(Component p, InputStream audioResource, Runnable task) {
		try (AudioInputStream ss = AudioSystem.getAudioInputStream(audioResource);
		     Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, ss.getFormat()))) {
			SecondaryLoop loop = p.getToolkit().getSystemEventQueue().createSecondaryLoop();
			clip.addLineListener(e -> {
				LineEvent.Type t = e.getType();
				if (Objects.equals(t, LineEvent.Type.STOP) || Objects.equals(t, LineEvent.Type.CLOSE)) {
					loop.exit();
				}
			});
			clip.open(ss);
			// 播放
			clip.start();
			// 做一些其他事情
			if (task != null) task.run();
			// 该方法将阻塞调用线程，并在新的事件循环结束时返回true
			loop.enter();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			UIManager.getLookAndFeel().provideErrorFeedback(p);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 通知用户有问题。我们默认使用 Toolkit beep 方法。
	 * 如果系统属性"jide.beepNotifyUser"不为true的话，则不进行任何操作(默认是true的)
	 */
	public static void notifyUser() {
		notifyUser(null);
	}

	/**
	 * 通知用户有问题。我们默认使用 Toolkit beep 方法。
	 * 如果系统属性"jide.beepNotifyUser"不为true的话，则不进行任何操作(默认是true的)
	 *
	 * @param component 有错误的组件，如果错误与任何组件无关，则为 null。
	 */
	public static void notifyUser(Component component) {
		String beep = System.getProperty("jide.beepNotifyUser", "true");
		if ("true".equalsIgnoreCase(beep)) {
			UIManager.getLookAndFeel().provideErrorFeedback(component);
		}
	}
}
