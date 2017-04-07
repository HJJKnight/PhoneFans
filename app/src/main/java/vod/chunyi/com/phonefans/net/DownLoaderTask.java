package vod.chunyi.com.phonefans.net;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import vod.chunyi.com.phonefans.PhoneFansApplication;

/**
 * 从服务器下载压缩文件
 *
 * @author Administrator
 *
 */
public class DownLoaderTask extends AsyncTask<Void, Integer, Long> {
	private final String TAG = "DownLoaderTask";
	/**
	 * 下载地址
	 */
	private URL mUrl;

	/**
	 * 下载的文件名
	 */
	private File mFile;
	private ProgressReportingOutputStream mOutputStream;
	private final Context mContext;
	private String filename;

	/**
	 * 初始化下载 线程
	 *
	 * @param url
	 *            下载地址
	 * @param out
	 *            下载文件的存放地址
	 * @param context
	 *            上下文
	 *
	 */
	public DownLoaderTask(String url, String out, Context context) {
		super();

		mContext = context;
		try {
			mUrl = new URL(url);
			filename = new File(mUrl.getFile()).getName();
			mFile = new File(out, filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected Long doInBackground(Void... params) {
		return download();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {

	}

	@Override
	protected void onPostExecute(Long result) {

		// 下载完成后 调用解压方法
		((PhoneFansApplication) mContext).doZipExtractorWork(filename);
	}

	/**
	 * 开始下载
	 */
	private long download() {
		URLConnection connection = null;
		int bytesCopied = 0;
		try {
			connection = mUrl.openConnection();
			int length = connection.getContentLength();

			// 如果存在此文件且大小一样
			if (mFile.exists() && length == mFile.length()) {

				Log.d(TAG, "file ******************" + mFile.getName()
						+ "**************************** already exits!!");
				return 0l;
			} else {
				// 向网络端下载
				mOutputStream = new ProgressReportingOutputStream(mFile);
				publishProgress(0, length);
				bytesCopied = copy(connection.getInputStream(), mOutputStream);
				mOutputStream.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytesCopied;
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
		}

	}
}
