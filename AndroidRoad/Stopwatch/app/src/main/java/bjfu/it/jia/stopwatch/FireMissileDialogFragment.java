package bjfu.it.jia.stopwatch;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;

public class FireMissileDialogFragment extends DialogFragment {
    private DialogInterface.OnClickListener positiveCallback;
    private DialogInterface.OnClickListener negativeCallback;

    private String title;
    private String message;

    // ERROR! cannot find android.support.annotation.StringRes
    // ERROR! cannot resolve symbol StringRes
    // public void show(@StringRes int title, @StringRes int message,...)
    // -> add Context as parameter to read value from resources.
    public void show(Context context, @StringRes int title, @StringRes int message, DialogInterface.OnClickListener positiveCallback,
                     DialogInterface.OnClickListener negativeCallback, FragmentManager fragmentManager) {
        this.title = context.getResources().getString(title);
        this.message = context.getResources().getString(message);
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;
        show(fragmentManager, "FireMissileDialogFragment");
    }
    // Overload: Hardcore Encoding
    public void show(String title, String message, DialogInterface.OnClickListener positiveCallback,
                     DialogInterface.OnClickListener negativeCallback, FragmentManager fragmentManager) {
        this.title = title;
        this.message = message;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;
        show(fragmentManager, "FireMissileDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.fire, positiveCallback);
        builder.setNegativeButton(R.string.cancel, negativeCallback);
        return builder.create();
    }
}