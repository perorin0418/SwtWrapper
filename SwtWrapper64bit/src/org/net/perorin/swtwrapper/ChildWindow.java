package org.net.perorin.swtwrapper;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public class ChildWindow {

	private List<ChildWindow> childList = null;
	private long hwnd = -1;
	private String className = "";

	public ChildWindow(long hwnd) {
		this.hwnd = hwnd;

		TCHAR _className = new TCHAR(OS.CP_INSTALLED, 256);
		OS.GetClassName(hwnd, _className, 256);
		className = _className.toString(0, _className.strlen());

		childList = Win32Util.getChilds(hwnd);
	}

	public void sendText(String text) {
		for (int i = 0; i < text.length(); i++) {
			OS.SendMessage(hwnd, OS.WM_CHAR, text.charAt(i), 0);
		}
	}

	public String getText() {
		int length = OS.GetWindowTextLength(hwnd) + 1;
		TCHAR text = new TCHAR(OS.CP_INSTALLED, length);
		OS.GetWindowText(hwnd, text, length);
		return text.toString(0, text.strlen());
	}

	public void sendKey(int key) {
		OS.SendMessage(hwnd, OS.WM_KEYDOWN, key, 0);
		OS.SendMessage(hwnd, OS.WM_KEYUP, key, 0);
	}

	public void click() {
		OS.SendMessage(hwnd, OS.BM_CLICK, 0, 0);
	}

	/**
	 * 子ウィンドウを持つか
	 *
	 * @return 子ウィンドウを持つか
	 */
	public boolean hasChild() {
		return !childList.isEmpty();
	}

	public List<ChildWindow> getChildList() {
		return childList;
	}

	public List<ChildWindow> getAllChildList() {
		List<ChildWindow> ret = new LinkedList<ChildWindow>();
		for (ChildWindow cw : childList) {
			ret.add(cw);
			if (cw.hasChild()) {
				ret.addAll(cw.getAllChildList());
			}
		}
		return ret;
	}

	public ChildWindow get(int index) {
		return childList.get(index);
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
