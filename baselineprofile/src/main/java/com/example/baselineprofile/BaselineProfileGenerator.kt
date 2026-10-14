package com.example.baselineprofile


/* How to implement this baseLineProfile is,

  1) very First go to File --> New Module --> Here left side select a Template called "Baseline Profile Generator"
  and create new baselineProfile module.

  2) As soon as creates a new module, it has two files called
       a) StartupBenchmarks.kt file to messaure performance of our app.
       b) BaselineProfileGenerator.kt file - It capture what are all actions we are doing for optimization on App start time.

  3) Now first at StartupBenchmarks.kt file write a test to find the performance of app. Here you can check startupCompilationNone() test case where it has CompilationMode None.

     It will call fun called  fun benchmark(compilationMode: CompilationMode) --> Here mainly write a flows what your app wnats to capture performance.
     All unitTests we write here would be exeucted using UiAutomator.
       (Example Open a App --> Spalsh screen LOGIN --> enter username and password --> MainScreen here scroll down)  for all of these actions write a unitTest and then run test.

     When a benchmark test finishes, It will create a result it saves a detailed JSON report inside your project's
     build/outputs/connected_android_test_additional_output/ folder.

     It looks like this:

        {
  "context": {
    "build": { "id": "12345", "version": "1.0.0" },
    "device": { "model": "Pixel 7", "apiLevel": 34 }
  },
  "benchmarks": [
    {
      "name": "measureStartupWithBaselineProfile",
      "metrics": {
        "startupDurationMs": {
          "minimum": 610.0, -->p0
          "maximum": 1150.0, -->p100
          "median": 680.0,  -->p50
          "percentile90": 890.0,
          "percentile95": 1010.0,
          "percentile99": 1120.0,
          "runs": [610.0, 650.0, 680.0, 670.0, 710.0, 890.0, 1010.0, 1150.0, 690.0, 660.0]
        }
      }
    }
  ]
}

To calculate these percentiles, Macrobenchmark doesn't guess. It uses simple sorting mathematics based on the number of iterations you set in your test code.
Let’s say you configure your benchmark to run iterations = 10 (the app opens 10 times).

Step 1: The system collects raw data from the 10 runs Run 1: 700ms Run 2: 1200ms (Phone staggered background sync)Run 3: 650ms Run 4: 680ms Run 5: 900ms (CPU throttling) Run 6: 660ms Run 7: 640ms Run 8: 690ms Run 9: 670ms Run 10: 1450ms (Worst run, heavy lag)

Step 2: The system sorts the data from Fastest to SlowestTo calculate percentiles, the math formula requires sorting the array first:640ms (Minimum / p0)650ms 660ms 670ms 680ms (Median / p50)690ms 700ms 900ms 1200ms (p90 - 9th position)1450ms (p95 / Maximum - 10th position)

Step 3: Picking the Percentile Valuesp50 (Median): The middle value (around 680ms).p90: The value at the 90% mark of your sorted list. Out of 10 items, the 9th item is 1200ms.
This tells you that 90% of your users will open the app in 1200ms or faster.p95: The value at the 95% mark. Because 10 samples are small, the system interpolates or picks the highest boundary (1450ms).
If you want a highly accurate p99, you would increase your test configurations to iterations = 100. In a sorted list of 100 app startups, the 99th slowest startup time becomes your p99 value.

===> In general a phone must draw a frame in under 16.6 milliseconds (for a standard 60Hz screen). 8.3 ms for 120Hz screen.

Above 10 iteration values are way beyond 16.6ms hence we have to implement baseLineProfile.


  4) Here at BaselineProfileGenerator.kt file, write a test class called fun generate() and write logic whatever flows we wanted to optimize
   Like i want to optimize this (Example Open a App --> Spalsh screen LOGIN --> enter username and password --> MainScreen here scroll down) path during app launch by ART

   I will write logic for the same using UIAutomator test and run the module or run using  ./gradlew :app:generateBaselineProfile then it generates a file
   called  baseline-prof.txt at  app/src/main/baselineProfiles/baseline-prof.txt

    a) Now if you miss to cover some flows ther you can override baseline-prof.txt by mentioing those paths but next auto generate file whatever you added would be overriden hence
    better not to touch this file.

    How this file would like ...
HPLcom/example/myapp/MainActivity;
HSPLcom/example/myapp/databinding/ActivityMainBinding;
Lcom/example/myapp/ui/dashboard/DashboardViewModel;
HSPLcom/example/myapp/network/ApiService;->fetchUserData(Ljava/lang/String;)Lcom/example/myapp/models/User;
+Lcom/example/myapp/ui/adapters/CustomAdapter;

What the symbols mean:--

   H (Hot): This code is executed frequently.
   S (Startup): This code is needed exactly during app startup.
   P (Post-Startup): This code is executed after the app opens.
   L: Represents the class path.
   ->methodName(...): Tells ART to pre-compile that specific method.

 5) After generating baseLineProfile we wants to validate is app performance improved or not for that we have to execute test again present at StartupBenchmarks.kt file (fun benchmark)
    with CompilationMode.Partial(BaselineProfileMode.Require) then it will pick baseLineProfile and execute tests here we have iterations count 10. That means 10 times app opens
    at this time it will open in fast so if we capture p90, p95 and p99 values it would be less than 16.6ms now. Basically we can see 30~40% optimization.

    How this work in Real prod app ....

    Here as soon as baseline-prof.txt it would be part of our apk/.aab file. While uploading to PlayStore, Google cloud platform picks and convert this txt file byte code
    and during installation ART (Android Run Time) picks and converts files or paths present in baseLine Profile convert them to odex files ahed of time by skipping JIT compiler.

    WITH THIS we can see optimization during app launch.
*/



import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class generates a basic startup baseline profile for the target package.
 *
 * We recommend you start with this but add important user flows to the profile to improve their performance.
 * Refer to the [baseline profile documentation](https://d.android.com/topic/performance/baselineprofiles)
 * for more information.
 *
 * You can run the generator with the "Generate Baseline Profile" run configuration in Android Studio or
 * the equivalent `generateBaselineProfile` gradle task:
 * ```
 * ./gradlew :app:generateReleaseBaselineProfile
 * ```
 * The run configuration runs the Gradle task and applies filtering to run only the generators.
 *
 * Check [documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args)
 * for more information about available instrumentation arguments.
 *
 * After you run the generator, you can verify the improvements running the [StartupBenchmarks] benchmark.
 *
 * When using this class to generate a baseline profile, only API 33+ or rooted API 28+ are supported.
 *
 * The minimum required version of androidx.benchmark to generate a baseline profile is 1.2.0.
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        // The application id for the running build variant is read from the instrumentation arguments.
        rule.collect(
            packageName = InstrumentationRegistry.getArguments().getString("targetAppId")
                ?: throw Exception("targetAppId not passed as instrumentation runner arg"),

            // See: https://d.android.com/topic/performance/baselineprofiles/dex-layout-optimizations
            includeInStartupProfile = true
        ) {
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll through your most important UI.

            // Start default activity for your app
            pressHome()
            startActivityAndWait()

            // TODO Write more interactions to optimize advanced journeys of your app.
            // For example:
            // 1. Wait until the content is asynchronously loaded
            // 2. Scroll the feed content
            // 3. Navigate to detail screen

            // Check UiAutomator documentation for more information how to interact with the app.
            // https://d.android.com/training/testing/other-components/ui-automator
        }
    }
}

@Entity(tablename = "users")
data class User(
    @PrimaryKey
    @SerilizedName("id")
    val id : Int,

    @SerilizedName("name")
    val name: String,
)

interface ApiService {
    @GET("users")
    suspend fun getAllUsers : List<User>
}

object RetrofitClient {
    val apiService : ApiService by lazy {
        Retrofit.Builder()
            .addBaseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterfactory()
            .client(httpclient = OkHttpclient.Builder().addInterceptor().build())
            .build()
            .create(ApiService::class.java) ///finally creating that service.
    }
}

