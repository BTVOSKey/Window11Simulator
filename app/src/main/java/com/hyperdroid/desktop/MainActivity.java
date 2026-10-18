package com.hyperdroid.desktop;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout desktop;
    int blue = Color.rgb(0, 120, 212);

    int dp(float n) {
        return (int)(n * getResources()
            .getDisplayMetrics().density + 0.5f);
    }

    GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }

    TextView text(String value, int size) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
        return t;
    }

    Button button(String value) {
        Button b = new Button(this);
        b.setText(value);
        b.setAllCaps(false);
        b.setTextColor(Color.WHITE);
        b.setBackground(shape(0xCC173F70, 12));
        return b;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(0xFF08254C);
        getWindow().setNavigationBarColor(0xFF08254C);

        desktop = new LinearLayout(this);
        desktop.setOrientation(LinearLayout.VERTICAL);
        desktop.setPadding(dp(14), dp(18), dp(14), dp(8));

        GradientDrawable wallpaper = new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[] {
                0xFF168DCE,
                0xFF07549B,
                0xFF071D48
            }
        );

        desktop.setBackground(wallpaper);
        setContentView(desktop);

        TextView title = text("HyperDroid", 28);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        desktop.addView(title,
            new LinearLayout.LayoutParams(-1, dp(55)));

        TextView subtitle = text(
            "Windows 11 Android Desktop", 13);
        subtitle.setGravity(Gravity.LEFT);

        desktop.addView(subtitle);

        LinearLayout area = new LinearLayout(this);
        area.setOrientation(LinearLayout.VERTICAL);
        area.setPadding(0, dp(24), 0, 0);

        desktop.addView(area,
            new LinearLayout.LayoutParams(-1, 0, 1));

        addButton(area, "🖥  This PC", this::explorer);
        addButton(area, "⌨  Terminal", this::terminal);
        addButton(area, "📦  Run EXE", this::chooseExe);
        addButton(area, "⚙  Settings", this::settings);

        LinearLayout taskbar = new LinearLayout(this);
        taskbar.setGravity(Gravity.CENTER);
        taskbar.setBackground(shape(0xDDE8F3F8, 14));

        Button start = new Button(this);
        start.setText("⊞ Start");
        start.setAllCaps(false);
        start.setTextColor(blue);
        start.setBackgroundColor(Color.TRANSPARENT);
        start.setOnClickListener(v -> startMenu());

        taskbar.addView(start);

        desktop.addView(taskbar,
            new LinearLayout.LayoutParams(-1, dp(52)));
    }

    void addButton(LinearLayout area, String name,
                   Runnable action) {
        Button b = button(name);
        b.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        b.setPadding(dp(12), 0, 0, 0);
        b.setOnClickListener(v -> action.run());

        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(-1, dp(52));

        p.setMargins(0, dp(5), 0, dp(5));
        area.addView(b, p);
    }

    void startMenu() {
        String[] items = {
            "This PC", "Terminal", "Run EXE", "Settings"
        };

        new AlertDialog.Builder(this)
            .setTitle("Start Menu")
            .setItems(items, (d, i) -> {
                if (i == 0) explorer();
                if (i == 1) terminal();
                if (i == 2) chooseExe();
                if (i == 3) settings();
            }).show();
    }

    void explorer() {
        new AlertDialog.Builder(this)
            .setTitle("File Explorer")
            .setMessage(
                "Desktop\nDocuments\nDownloads\nPictures\n\n" +
                "Đây là trình quản lý file mô phỏng.")
            .setPositiveButton("OK", null)
            .show();
    }

    void settings() {
        new AlertDialog.Builder(this)
            .setTitle("Settings")
            .setMessage(
                "HyperDroid 0.1\n\n" +
                "Desktop: Ready\n" +
                "Windows Runtime: Not installed\n" +
                "EXE execution: Not available yet")
            .setPositiveButton("OK", null)
            .show();
    }

    void terminal() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(12), dp(12), dp(12), dp(12));
        layout.setBackgroundColor(0xFF101214);

        TextView output = new TextView(this);
        output.setTextColor(0xFF7DFF8A);
        output.setTypeface(Typeface.MONOSPACE);
        output.setText("HyperDroid Terminal\nType help\n\n");

        layout.addView(output);

        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setTextColor(Color.WHITE);
        input.setHintTextColor(Color.GRAY);
        input.setHint("C:\\Users\\User>");
        input.setInputType(1);
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        layout.addView(input);

        AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle("Terminal")
            .setView(layout)
            .setNegativeButton("Close", null)
            .create();

        input.setOnEditorActionListener((v, action, event) -> {
            String cmd = input.getText().toString().trim();
            String result;

            switch (cmd.toLowerCase()) {
                case "help":
                    result = "help, ver, dir, whoami, cls";
                    break;
                case "ver":
                    result = "HyperDroid Simulator 0.1";
                    break;
                case "dir":
                    result = "Desktop Documents Downloads";
                    break;
                case "whoami":
                    result = "hyperdroid-user";
                    break;
                case "cls":
                    output.setText("");
                    input.setText("");
                    return true;
                default:
                    result = "Unknown simulated command";
            }

            output.append("C:\\Users\\User> " + cmd +
                "\n" + result + "\n\n");

            input.setText("");
            return true;
        });

        dialog.show();
    }

    void chooseExe() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(intent, 101);
        } catch (Exception e) {
            Toast.makeText(this, "Cannot open file picker",
                Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(
        int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK
                && data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                new AlertDialog.Builder(this)
                    .setTitle("EXE selected")
                    .setMessage(
                        uri.toString() +
                        "\n\nWine runtime chưa được tích hợp. " +
                        "Chưa thể chạy file EXE thật.")
                    .setPositiveButton("OK", null)
                    .show();
            }
        }
    }
}
