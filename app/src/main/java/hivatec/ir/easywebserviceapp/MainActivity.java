package hivatec.ir.easywebserviceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import hivatec.ir.easywebservice.Callback;
import hivatec.ir.easywebservice.EasyWebservice;
import hivatec.ir.easywebservice.Method;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		JSONObject json = null;

		try {
			json = new JSONObject("{\"type\" : \"json\"}");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		new EasyWebservice("http://www.otooapp.com/api/v2/categoryList")
				.method(Method.POST)
				.addParam("token", "ea333f21e122858470a3996fc3511e36781d318d2e3f6fd89b608592586c733403b6ee9c")
				.addParam("districtId", 4)
				.call(new Callback.A<Config2>() {
					@Override
					public void onSuccess(Config2 config) {

					}

					@Override
					public void onError(String error) {

					}
				});
	}

}
