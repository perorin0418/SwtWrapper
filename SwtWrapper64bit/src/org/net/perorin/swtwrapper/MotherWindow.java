package org.net.perorin.swtwrapper;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

/**
 * Windowクラスを構成する一部品
 * <p>
 * ウィンドウそのもののハンドルを管理する
 *
 * @author perorin
 */
public class MotherWindow {

	/**
	 * ウィンドウ名
	 */
	private String titleName = "";

	/**
	 * ウィンドウのハンドル
	 */
	private long hwnd = -1;

	/**
	 * ウィンドウのクラス名
	 */
	private String className = "";

	/**
	 * コンストラクタ
	 *
	 * @param title ウィンドウ名
	 * @throws HwndNotFoundException
	 */
	public MotherWindow(String title) throws HwndNotFoundException {
		titleName = title;
		hwnd = OS.FindWindow(null, new TCHAR(OS.CP_INSTALLED, titleName, true));
		if (hwnd == 0) {
			throw new HwndNotFoundException("指定したタイトル[" + title + "]のハンドルが見つかりません");
		}

		TCHAR _className = new TCHAR(OS.CP_INSTALLED, 256);
		OS.GetClassName(hwnd, _className, 256);
		className = _className.toString(0, _className.strlen());
	}

	public String getTitleName() {
		return titleName;
	}

	public long getHwnd() {
		return hwnd;
	}

	public String getHwndHex() {
		return Long.toHexString(hwnd);
	}

	public String getClassName() {
		return className;
	}
}
