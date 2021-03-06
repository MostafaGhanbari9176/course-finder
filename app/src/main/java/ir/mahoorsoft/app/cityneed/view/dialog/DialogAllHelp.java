package ir.mahoorsoft.app.cityneed.view.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ir.mahoorsoft.app.cityneed.G;
import ir.mahoorsoft.app.cityneed.R;

/**
 * Created by M_gh on 12/10/2017.
 */

public class DialogAllHelp implements View.OnClickListener {

    private Context context;
    private View view;
    private Dialog dialog;
    private Button btn;
    ArrayList<TextView> views;

    public DialogAllHelp(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        views = new ArrayList<>();
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.dialog_all_help, null, false);
    }

    public void Show() {

        views.add((TextView) view.findViewById(R.id.txtLoginHelpDialog));
        views.add((TextView) view.findViewById(R.id.txtSabtenamHelpDialog));
        views.add((TextView) view.findViewById(R.id.txtTrendingUpHelpDialog));
        views.add((TextView) view.findViewById(R.id.txtAddCourseHelpDialog));
        views.add((TextView) view.findViewById(R.id.txtManageStudentHelpDialog));
        views.add((TextView) view.findViewById(R.id.txtSearchHelpDialog));
        views.add((TextView) view.findViewById(R.id.txtCommentHelpDialog));
        views.add((TextView) view.findViewById(R.id.txtApphHelpDialog));
        setText();
        ((Button) view.findViewById(R.id.btnLoginHelpDialog)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnSabtenamHelpDialog)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnTrendingUpHelpDialog)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnAddCourseHelpDialog)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnManageStudentHelpDialog)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnSearchHelpDialog)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnCommentHelpDialog)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnAppHelpDialog)).setOnClickListener(this);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }


    private void setText() {
        views.get(0).setText("برای ورود به حساب کاربری کافیه بروی کلید حساب کاربری در پایین صفحه خانه کلیک کرده و ایمیل خودرا به همراه نام و نام خانوادگی وارد کنید و کلید تایید ایمیل را فشار دهید پس از دریافت ایمیل اعتبار سنجی کد موجود در آن را وارد کرده و کلید بررسی صحت کدرا فشار دهید"+"\n\n");
        views.get(1).setText("برای ثبت نام در یک دوره کافیه بروی دوره مورد نظر کلیک کرده و پس از نمایش مشخصات دوره در انتهای مشخصات دوره بروی کلید ثبت نام کلیک کنید با این کار ثبت نام موقت شما انجام شده و آموزشگاه با شما ارتباط برقرار می کند"+"\n\n");
        views.get(2).setText("برای ثبت دوره باید کاربری خود را به مدرس ارتقاء دهید که برای اینکار کافیه به صفحه پروفایل رفته و بروی ارتقاء به مدرس کلیک کنید"+"\n\n");
        views.get(3).setText("برای ثبت یک دوره جدید ابتدا باید از طرف ما تایید اعتبار شده و پس از خرید اشتراک با استفاده از کلید اضافه کردن در پایین صفحه پروفایل دوره خودرا ثبت کنید"+"\n\n");
        views.get(4).setText("برای مدیریت محصلین و همچنین دوره هایی که ثبت کرده اید کافیه در صفحه پروفایل بروی کلید اضافه شده کلیک کرده تا لیست دوره های ثبت شده توسط خودرا ملاحظه کنید با کلیک بروی هر دوره می توانید لیست محصلین را مشاهده کرده دوره را ویرایش کرده و یک ویدیو مرتبط با دوره بارگذاری کرد"+"\n\n");
        views.get(5).setText("برای جستجو کافیه بروی کلید جستجو در پایین صفحه خانه کلیک کرده پس از ورود به صفحه جستجو می توانبد بخشی از نام دوره یا آموزشگاه موردنظر را جستجو کرده و براساس برخی موارد نتیجه را فیلتر کنید"+"\n\n");
        views.get(6).setText("برای نظر و امتیاز دادن درمورد یک دوره یا آموزشگاه کافیه بروی آموزشگاه یا دوره موردنظر کلیک کرده و در صفحه نظرات و امتیازات نظر و امتیاز خودرا ثبت کرده پس از بررسی متن نظر شما توسط تیم ما قابل نمایش خواهد بود"+"\n\n");
        views.get(7).setText("درهر صفحه در صورت ناقص بارگزاری شدن اطلاعات از سرور یا اعلام پیام (خطا,درارتباط با سرور!) کافیه به پایین اسکرول کنید تا اطلاعات صفحه بروزرسانی شود."+"\n\n"+"برای تنظیم یادآور هفتگی و یادآور فرارسیدن تاریخ برگزاری یک دوره به قسمت دوره های ثبت نام شده رفته و با کلیک بروی دکمه منویه هردوره یادآور را فعال کنید"+"\n\n"+"برای فعال کردن یادآور یک دوره, ثبت نام در آن دوره باید از طرف آموزشکاه تایید شده باشد"+"\n\n");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLoginHelpDialog:
                changeViewVisibility((TextView) view.findViewById(R.id.txtLoginHelpDialog));
                break;
            case R.id.btnSabtenamHelpDialog:
                changeViewVisibility((TextView) view.findViewById(R.id.txtSabtenamHelpDialog));
                break;
            case R.id.btnManageStudentHelpDialog:
                changeViewVisibility((TextView) view.findViewById(R.id.txtManageStudentHelpDialog));
                break;
            case R.id.btnTrendingUpHelpDialog:
                changeViewVisibility((TextView) view.findViewById(R.id.txtTrendingUpHelpDialog));
                break;
            case R.id.btnSearchHelpDialog:
                changeViewVisibility((TextView) view.findViewById(R.id.txtSearchHelpDialog));
                break;
            case R.id.btnAddCourseHelpDialog:
                changeViewVisibility((TextView) view.findViewById(R.id.txtAddCourseHelpDialog));
                break;
            case R.id.btnCommentHelpDialog:
                changeViewVisibility((TextView) view.findViewById(R.id.txtCommentHelpDialog));
                break;
            case R.id.btnAppHelpDialog:
                changeViewVisibility((TextView) view.findViewById(R.id.txtApphHelpDialog));
                break;
        }
    }

    private void changeViewVisibility(TextView txt) {

        if (txt.getVisibility() == View.GONE)
            G.animatingForVisible(txt, 0f, 1f, 0);
        else
            G.animatingForGone(txt, 1f, 0f, -txt.getHeight());
        for (int i = 0; i < views.size(); i++) {
            if (views.get(i) == txt)
                continue;
            if (views.get(i).getVisibility() == View.VISIBLE)
                G.animatingForGone(views.get(i), 1f, 0f, -views.get(i).getHeight());
        }
    }

}
