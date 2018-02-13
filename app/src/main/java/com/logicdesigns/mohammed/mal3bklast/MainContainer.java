package com.logicdesigns.mohammed.mal3bklast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandler;
import com.logicdesigns.mohammed.mal3bklast.Interface.OnSwipeListener;
import com.logicdesigns.mohammed.mal3bklast.Models.PlayerPositions;
import com.logicdesigns.mohammed.mal3bklast.PushNotification.Config;
import com.logicdesigns.mohammed.mal3bklast.PushNotification.NotificationUtils;
import com.logicdesigns.mohammed.mal3bklast.bottomNavigation.BottomNavigationViewHelper;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.fragments.ChatDetailsFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.ChoosePlaygroudFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.ReservePlaygroundContainer;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.logicdesigns.mohammed.mal3bklast.pager.TabsPagerAdapter;
import com.logicdesigns.mohammed.mal3bklast.pager.ViewPagerAdapter;
import com.logicdesigns.mohammed.mal3bklast.tasks.GetCitiesTask;
import com.logicdesigns.mohammed.mal3bklast.tasks.GetPlayerPostions;
import com.logicdesigns.mohammed.mal3bklast.tasks.GetPlaygroundTypesTask;
import com.logicdesigns.mohammed.mal3bklast.tasks.NotificationBadgeTask;
import com.logicdesigns.mohammed.mal3bklast.tasks.PlaygroundFacilities_Task;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.util.ArrayList;
import java.util.List;

import BusinessClasses.User;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainContainer extends AppCompatActivity implements OnSwipeListener {

    // public static Boolean checkonHomeIC=false;
    BottomNavigationView bottomNavigationView;

    MenuItem prevMenuItem;
    TextView title_header_fragment,title_header_fragment2;
    TextView badge_notifiaction;
    ViewPager viewPager;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static boolean active = false;

   public static int headerNum=0;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        active = true;
        setContentView(R.layout.activity_main_container);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
        viewPager = (ViewPager) findViewById(R.id.placeholder2);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/DIN Next LT W23 Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        SharedPreferences mPrefs4 = PreferenceManager.getDefaultSharedPreferences(MainContainer.this);
        String json4 = mPrefs4.getString("userObject", "");
        Gson gson4 = new Gson();
        User obj = gson4.fromJson(json4, User.class);
        String userID  = String.valueOf(obj.getId());


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_fragment:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.register_pg_fragment:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.search_pl_fragment:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.search_trg_fragment:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.profile_fragment:
                                viewPager.setCurrentItem(4);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                LinearLayout ll = (LinearLayout) findViewById(R.id.profile_photo);
                setIds();
                ImageButton logoutHeader1 = (ImageButton) findViewById(R.id.logoutHeader1);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainContainer.this);
                String language = sharedPref.getString("language", "");
                if (language.equals("en")) {
                    RelativeLayout.LayoutParams parent = (RelativeLayout.LayoutParams) ll.getLayoutParams();
                    parent.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    ll.setLayoutParams(parent);
                }
                title_header_fragment  = (TextView) findViewById(R.id.title_header_fragment);

                RelativeLayout relative_layout_notification = (RelativeLayout) findViewById(R.id.relative_layout_notification);
                RelativeLayout relative_layout_message = (RelativeLayout)  findViewById(R.id.relative_layout_message);

                if (language.equals("en")) {
                    if (position == 0) {
                        logoutHeader1.setVisibility(View.VISIBLE);
                        title_header_fragment.setText(R.string.Offers);
                        ll.setVisibility(View.GONE);
                        relative_layout_notification.setVisibility(View.VISIBLE);
                        relative_layout_message.setVisibility(View.GONE);
                        showItems();
                    } else if (position == 1) {
                        logoutHeader1.setVisibility(View.VISIBLE);
                        ll.setVisibility(View.GONE);
                        title_header_fragment.setText(R.string.playground);
                        showItems();
                        relative_layout_notification.setVisibility(View.VISIBLE);
                        relative_layout_message.setVisibility(View.GONE);
                    } else if (position == 2) {
                        logoutHeader1.setVisibility(View.VISIBLE);
                        title_header_fragment.setText(R.string.SearchPlayer);
                        ll.setVisibility(View.GONE);
                        showItems();
                        relative_layout_notification.setVisibility(View.VISIBLE);
                        relative_layout_message.setVisibility(View.GONE);
                    } else if (position == 3) {
                        logoutHeader1.setVisibility(View.VISIBLE);
                        title_header_fragment.setText(R.string.SearchExercise);
                        ll.setVisibility(View.GONE);
                        showItems();
                        relative_layout_notification.setVisibility(View.VISIBLE);
                        relative_layout_message.setVisibility(View.GONE);
                    } else if (position == 4) {
                        logoutHeader1.setVisibility(View.INVISIBLE);
                        ll.setVisibility(View.VISIBLE);
                        SharedPreferences mPrefs1 = PreferenceManager.getDefaultSharedPreferences(MainContainer.this);
                        String json2 = mPrefs1.getString("userObject", "");
                        Gson gson1 = new Gson();
                        User obj = gson1.fromJson(json2, User.class);
                        String userObject = String.valueOf(obj.getName());
                        title_header_fragment.setText(userObject);
                        hideHeaderItems();
                        relative_layout_notification.setVisibility(View.GONE);
                        relative_layout_message.setVisibility(View.GONE);

                    }
                } else {
                    if (position == 0) {
                        logoutHeader1.setVisibility(View.VISIBLE);
                        title_header_fragment.setText("اخر العروض");
                        ll.setVisibility(View.GONE);
                        showItems();
                        relative_layout_notification.setVisibility(View.VISIBLE);
                        relative_layout_message.setVisibility(View.GONE);
                    } else if (position == 1) {
                        logoutHeader1.setVisibility(View.VISIBLE);
                        ll.setVisibility(View.GONE);
                        title_header_fragment.setText("حجز ملعب");
                        showItems();
                        relative_layout_notification.setVisibility(View.VISIBLE);
                        relative_layout_message.setVisibility(View.GONE);
                    } else if (position == 2) {
                        logoutHeader1.setVisibility(View.VISIBLE);
                        title_header_fragment.setText("البحث عن لاعب");
                        ll.setVisibility(View.GONE);
                        showItems();
                        relative_layout_notification.setVisibility(View.VISIBLE);
                        relative_layout_message.setVisibility(View.GONE);
                    } else if (position == 3) {
                        logoutHeader1.setVisibility(View.VISIBLE);
                        title_header_fragment.setText("البحث عن تمرين");
                        ll.setVisibility(View.GONE);
                        showItems();
                        relative_layout_notification.setVisibility(View.VISIBLE);
                        relative_layout_message.setVisibility(View.GONE);
                    } else if (position == 4) {
                        logoutHeader1.setVisibility(View.INVISIBLE);
                        ll.setVisibility(View.VISIBLE);
                        SharedPreferences mPrefs3 = PreferenceManager.getDefaultSharedPreferences(MainContainer.this);
                        String json3 = mPrefs3.getString("userObject", "");
                        Gson gson3 = new Gson();
                        User obj = gson3.fromJson(json3, User.class);
                        String userObject = String.valueOf(obj.getName());
                        title_header_fragment.setText(userObject);
                        hideHeaderItems();
                        relative_layout_notification.setVisibility(View.GONE);
                        relative_layout_message.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Fragment header = getSupportFragmentManager().findFragmentByTag("header");
        if (header == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholder1, new HeaderFragment(), "header")

                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholder1, header, "header")

                    .commit();
        }



        setupViewPager(viewPager);


if (savedInstanceState!=null)
{
   // String myString = savedInstanceState.getString("pageHeaderSaved");
//    title_header_fragment.setText(myString);
}

        FirebaseMessaging.getInstance().subscribeToTopic("global");
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    String title = intent.getStringExtra("title");
                    String timestamp = intent.getStringExtra("timestamp");
                    String type = intent.getStringExtra("type");



                    TextView badge_messages = (TextView) findViewById(R.id.badge_messages);





                    Intent viewIntent;
                    PendingIntent viewPendingIntent ;
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    if (preferences.getString("userObject", "").length() > 0)
                    {
                        viewIntent = new Intent(getApplicationContext(), MainContainer.class);
                        viewPendingIntent =
                                PendingIntent.getActivity(getApplicationContext(), 0, viewIntent, 0);
                    }
                    else {
                        viewIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        viewPendingIntent =
                                PendingIntent.getActivity(getApplicationContext(), 0, viewIntent, 0);
                    }
                    RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_push_layout);
                    contentView.setImageViewResource(R.id.image, R.drawable.playgroundphoto);
                    contentView.setTextViewText(R.id.title, title);
                    contentView.setTextViewText(R.id.text, message);
                    contentView.setTextViewText(R.id.time_stamp_tv,timestamp);

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainContainer.this)
                            .setSmallIcon(R.drawable.playgroundphoto)
                            .setContent(contentView)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setContentIntent(viewPendingIntent);

                    Notification notification = mBuilder.build();
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    notification.defaults |= Notification.DEFAULT_SOUND;
                    notification.defaults |= Notification.DEFAULT_VIBRATE;
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(1, notification);
                }
            }
        };

        displayFirebaseRegId();


    }

    @Override
    public void onBackPressed() {
       FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {

            fm.popBackStack();

        } else {
            try {
                super.onBackPressed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
      //  RefreshUserInforamtion refreshUserInforamtion = new RefreshUserInforamtion(this);

    }

    @Override
    public void onSwipeClicked(int i) {
        Fotter_Fragment fotter_fragment = new Fotter_Fragment();
        fotter_fragment.updateFooterIcons(i, this);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Fragment main = new MainPageFragment();
        Fragment reg = new ReservePlaygroundContainer();
        Fragment search = new SearchAboutPlayerFragment();
        Fragment training = new SearchTrainingBoxFragment();
        Fragment profile = new ProfileFragment();

        adapter.addFragment(main);
        adapter.addFragment(reg);
        adapter.addFragment(search);
        adapter.addFragment(training);
        adapter.addFragment(profile);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);


        // viewPager.setPageTransformer(true, new BackgroundToForegroundTransformer());
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {


            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        //        @Override
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
    }

    @Override
    public void onResume(){
       super.onResume();
       active = true;
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }



    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("Firebase reg id", "Firebase reg id: " + regId);



        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainContainer.this);
        String json1 = mPrefs.getString("userObject", "");
        Gson gson = new Gson();
        User user_obj = gson.fromJson(json1, User.class);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://logic-host.com/work/kora/beta/phpFiles/setRegIdAPI.php?key=logic123&id="+user_obj.getId()+"&regId="+regId;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    ImageButton notification_icon_header1;
    ImageButton message_icon_header;
    RelativeLayout badge_notification;
    public  void setIds()
    {

         notification_icon_header1 = (ImageButton) findViewById(R.id.notification_icon_header1);
         message_icon_header = (ImageButton) findViewById(R.id.message_icon_header);
         badge_notification = (RelativeLayout) findViewById(R.id.relative_layout_notification);
    }
    public void hideHeaderItems()
    {

        notification_icon_header1.setVisibility(View.GONE);
        message_icon_header.setVisibility(View.GONE);
        if (badge_notifiaction!=null)
          if (badge_notifiaction.isShown())
            badge_notification.setVisibility( View.GONE);
    }
    private  void showItems()
    {

        notification_icon_header1.setVisibility(View.VISIBLE);
        message_icon_header.setVisibility(View.VISIBLE);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
      public  static double pageNumber = 0;

    @Override
    protected void onDestroy() {
        active = false;
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        active = false;
        super.onStop();
    }

}
