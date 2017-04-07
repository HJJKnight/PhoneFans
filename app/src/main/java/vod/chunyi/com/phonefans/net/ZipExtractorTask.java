package vod.chunyi.com.phonefans.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * ��ѹ�ļ���ָ��λ��
 * 
 * @author Administrator
 * 
 */
public class ZipExtractorTask extends AsyncTask<Void, Integer, Long> {
	private final String TAG = "ZipExtractorTask";

	/**
	 * ��ѹ�ļ�Դ
	 */
	private final File mInput;

	/**
	 * ��ѹ�ļ��������ļ�
	 */
	private final File mOutput;

	/**
	 * ������
	 */
	private final Context mContext;

	private Handler mhandler;

	/**
	 * ���ظ����߳� ���ز���ѹ���
	 */
	public static final int DOWN_UNZIP_FINISH = 0X255;

	/**
	 * ��ʼ�� ��ѹ�ļ����߳�
	 * 
	 * @param in
	 *            ��ѹ�ļ���λ��
	 * @param out
	 *            ��ѹ���ļ��Ĵ��λ��
	 * @param context
	 *            ������
	 * @param handler
	 *            ��ѹ��ɺ���Ϣ��ȥ
	 */
	public ZipExtractorTask(String in, String out, Context context,
			Handler handler) {
		super();
		mInput = new File(in);
		mOutput = new File(out);
		mContext = context;
		mhandler = handler;
	}

	@Override
	protected Long doInBackground(Void... params) {
		return unzip();
	}

	@Override
	protected void onPostExecute(Long result) {
		if (isCancelled())
			return;
		Message message = new Message();
		message.what = DOWN_UNZIP_FINISH;
		mhandler.sendMessage(message);
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
	}

	/**
	 * ��ѹ�ļ�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private long unzip() {

		long extractedSize = 0L;
		Enumeration<ZipEntry> entries;
		ZipFile zip = null;

		try {
			zip = new ZipFile(mInput);
			long uncompressedSize = getOriginalSize(zip);
			publishProgress(0, (int) uncompressedSize);

			entries = (Enumeration<ZipEntry>) zip.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (entry.isDirectory()) {
					continue;
				}
				File destination = new File(mOutput, entry.getName());

				if (!destination.getParentFile().exists()) {
					Log.e(TAG, "make="
							+ destination.getParentFile().getAbsolutePath());
					destination.getParentFile().mkdirs();
				}

				ProgressReportingOutputStream outStream = new ProgressReportingOutputStream(
						destination);
				extractedSize += copy(zip.getInputStream(entry), outStream);
				outStream.close();
			}
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {

				if (zip != null)
					zip.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return extractedSize;
	}

	private long getOriginalSize(ZipFile file) {
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) file.entries();
		long originalSize = 0l;
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (entry.getSize() >= 0) {
				originalSize += entry.getSize();
			}
		}
		return originalSize;
	}

	private int copy(InputStream input, OutputStream output) {
		byte[] buffer = new byte[1024 * 8];
		BufferedInputStream in = new BufferedInputStream(input, 1024 * 8);
		BufferedOutputStream out = new BufferedOutputStream(output, 1024 * 8);
		int count = 0, n = 0;
		try {
			while ((n = in.read(buffer, 0, 1024 * 8)) != -1) {
				out.write(buffer, 0, n);
				count += n;
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	private final class ProgressReportingOutputStream extends FileOutputStream {

		public ProgressReportingOutputStream(File file)
				throws FileNotFoundException {
			super(file);
		}

		@Override
		public void write(byte[] buffer, int byteOffset, int byteCount)
				throws IOException {
			super.write(buffer, byteOffset, byteCount);
			// mProgress += byteCount;
			// publishProgress(mProgress);
		}

	}

}
