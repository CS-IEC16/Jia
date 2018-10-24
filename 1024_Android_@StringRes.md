Usage of @StringRes
===============================
### Issue - 2018.10.24 11:00
* Error occured when passing `R.string.\*` to customized `@StringRes int para` type parameter.  
Error Message: `Cannot find package android.support.annotation.StringRes`.


### Fixed - 2018.10.24 11:58
* Add
```java
implementation 'com.android.support:support-annotations:27.1.1'
```
in `\app\gradle.build`. Build file in **app folder**!
* To read the real values of `@StringRes int`, also add `Context context` as para in the para list.
```java  
public void show(Context context, @StringRes int title) {
	String title = context.getResources().getString(title);
	system.out.println(title);		// real value of title
}
```
* [See Android Documentation: Improve code inspection with annotations](https://developer.android.com/studio/write/annotations?hl=zh-cn), but notice, `Configuration 'compile' is obsolete and has been replaced with 'implementation'`.
