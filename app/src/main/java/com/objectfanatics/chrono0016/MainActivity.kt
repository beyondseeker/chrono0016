package com.objectfanatics.chrono0016

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import permissions.dispatcher.*

// ・必須のアノテーションです。
// ・Activity もしくは Fragment に対して @RuntimePermissions アノテーションを付与することにより、
// 　PermissionsDispatcher による権限のハンドリングが有効になります。
@RuntimePermissions
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.show_camera_button).setOnClickListener {
            onShowCameraButtonClick()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 自動生成された権限ハンドリング用のコードに処理を委譲するための extension function を呼び出す。
        onRequestPermissionsResult(requestCode, grantResults)
    }

    /**
     * 画面中央の『カメラ起動』ボタンが押された時に呼び出されるメソッド。
     */
    private fun onShowCameraButtonClick() {
        // showCamera() を呼ぶのではなく、自動生成されたラッパーメソッドの showCameraWithPermissionCheck() を呼んでいる点に注意。
        showCameraWithPermissionCheck()
    }

    /**
     * カメラの権限を必要とする任意のメソッド。
     */
    // ・必須のアノテーションです。
    // ・`@NeedsPermission` アノテーションが付与されたメソッドに対しては、annotation processor により
    //   `WithPermissionCheck` が付与されたラッパーメソッドが自動的に生成され、開発者はそちらの
    // 　ラッパーメソッドを呼び出します。
    // ・ラッパーメソッド経由で権限のリクエストが許可された直後に PermissionsDispatcher により自動的に呼び出されます。
    // ・private スコープは使用できません。
    @NeedsPermission(Manifest.permission.CAMERA)
    fun showCamera() {
        Toast.makeText(this, "showCamera()", Toast.LENGTH_SHORT).show()
    }

    /**
     * 権限が必要な理由を表示する任意のメソッド。
     */
    // ・`@OnShowRationale` アノテーションが付与されたメソッドは、『今後表示しない』が設定されていない状態で
    // 　権限のリクエストが発生した直後に PermissionsDispatcher により自動的に呼び出されます。
    // ・引数に、続行か中断を指示できる PermissionRequest を指定することができます。これを用いると、
    // 　ダイアログに渡すことによりダイアログ側で続行か中断の指示を出すようなことが可能になります。
    // ・引数が指定されていない場合は、proceed${NeedsPermissionMethodName}ProcessRequest と cancel${NeedsPermissionMethodName}ProcessRequest
    // 　メソッドが @RuntimePermissions アノテーションが付与された Activity や Fragment に生成され、
    // 　これらのメソッドにより続行か中断の指示を出すことができます。
    // ・private スコープは使用できません。
    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        showRationaleDialog(request)
    }

    /**
     * 権限が拒否された場合に呼び出される任意のメソッド
     */
    // ・`@OnPermissionDenied` アノテーションが付与されたメソッドは、『今後表示しない』が設定されていない状態で
    // 　権限付与が拒否された場合に PermissionsDispatcher により自動的に呼び出されます。
    // ・private スコープは使用できません。
    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraPermissionDenied() {
        Toast.makeText(this, "onCameraPermissionDenied()", Toast.LENGTH_SHORT).show()
    }

    /**
     * 『今後表示しない』が選択された場合に呼び出される任意のメソッド
     */
    // `@OnNeverAskAgain` アノテーションが付与されたメソッドは、『今後表示しない』が設定された状態で
    // 権限付与が拒否された場合に PermissionsDispatcher により自動的に呼び出されます。
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        Toast.makeText(this, "onCameraNeverAskAgain()", Toast.LENGTH_SHORT).show()
    }

    /**
     * `@OnShowRationale` 経由で起動され、権限が必要な理由を表示して許可の是非を問うダイアログを表示するメソッド。
     */
    private fun showRationaleDialog(request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setPositiveButton("許可") { _, _ -> request.proceed() }
                .setNegativeButton("拒否") { _, _ -> request.cancel() }
                .setCancelable(false)
                .setMessage("カメラの権限が必要です")
                .show()
    }
}