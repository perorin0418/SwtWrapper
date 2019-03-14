package org.net.perorin.swtwrapper;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public class Window {

	private MotherWindow title = null;
	private List<ChildWindow> childList = null;

	public Window(String title) throws HwndNotFoundException {
		this.title = new MotherWindow(title);
		childList = Win32Util.getChilds(this.title.getHwnd());
	}

	/***
	 * nCmdShowの取りうる値<br>
	 * SW_FORCEMINIMIZE	ウィンドウを所有するスレッドがハングしても、ウィンドウを最小化する。<br>
	 * SW_HIDE	ウィンドウを非表示にし、他のウィンドウをアクティブにする<br>
	 * SW_MAXIMIZE	ウィンドウを最大化する<br>
	 * SW_MINIMIZE	ウィンドウを最小化し、Zオーダーが次のトップレベルウィンドウをアクティブにする。<br>
	 * SW_RESTORE	ウィンドウをアクティブにして表示する。最大化または最小化されていたウィンドウは元の位置とサイズに戻る。<br>
	 * SW_SHOW	ウィンドウをアクティブにして、現在の位置とサイズで表示する。<br>
	 * SW_SHOWMAXIMIZED	ウィンドウを最大化する<br>
	 * SW_SHOWMINIMIZED	ウィンドウを最小化する<br>
	 * SW_SHOWMINNOACTIVE	ウィンドウを現在のサイズと位置で表示する。<br>
	 * SW_SHOWNORMAL	ウィンドウをアクティブにして表示する。初めてウィンドウを表示するときには、このフラグを指定せよ。<br>
	 * @param nCmdShow
	 */
	public void showWindow(int nCmdShow) {
		OS.ShowWindow(title.getHwnd(), nCmdShow);
	}

	/***
	 * ウィンドウの位置を設定
	 * @param pos
	 */
	public void setWindowPos(Position pos) {
		setWindowPos(pos.getX(), pos.getY());
	}

	/***
	 * ウィンドウの位置を設定
	 * @param x
	 * @param y
	 */
	public void setWindowPos(int x, int y) {
		Size size = getWindowSize();
		OS.SetWindowPos(title.getHwnd(), 0, x, y, size.getWidth(), size.getHeight(), 0);
	}

	/***
	 * ウィンドウの高さ、幅を設定
	 * @param size
	 */
	public void setWindowSize(Size size) {
		setWindowSize(size.getWidth(), size.getHeight());
	}

	/***
	 * ウィンドウの高さ、幅を設定
	 * @param width
	 * @param height
	 */
	public void setWindowSize(int width, int height) {
		Position pos = getWindowPos();
		OS.SetWindowPos(title.getHwnd(), 0, pos.getX(), pos.getY(), width, height, 0);
	}

	/***
	 * ウィンドウの位置を取得
	 * @return
	 */
	public Position getWindowPos() {
		RECT rec = new RECT();
		OS.GetWindowRect(title.getHwnd(), rec);
		return new Position(rec.left, rec.top);
	}

	/***
	 * ウィンドウの幅、高さを取得
	 * @return
	 */
	public Size getWindowSize() {
		RECT rec = new RECT();
		OS.GetWindowRect(title.getHwnd(), rec);
		return new Size(rec.right - rec.left, rec.bottom - rec.top);
	}

	/***
	 * ウィンドウを最前面に出す
	 */
	public void foreGround() {
		OS.SetForegroundWindow(title.getHwnd());
	}

	public MotherWindow getTitle() {
		return title;
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

}
