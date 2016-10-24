package
{
	import flash.desktop.NativeApplication;
	import flash.events.Event;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.text.TextFormat;
	import flash.ui.Multitouch;
	import flash.ui.MultitouchInputMode;
	
	import flash.text.TextField;
	
	import com.amanitadesign.AndroidNative;
	import com.amanitadesign.events.RequestPermissionsResultEvent
	
	/**
	 * ...
	 * @author Oldes
	 */
	public class Main extends Sprite 
	{
		
		public var tf:TextField;
		
		public function Main() 
		{
			stage.scaleMode = StageScaleMode.NO_SCALE;
			stage.align = StageAlign.TOP_LEFT;
			stage.addEventListener(Event.DEACTIVATE, deactivate);
			
			// touch or gesture?
			Multitouch.inputMode = MultitouchInputMode.TOUCH_POINT;
			
			tf = new TextField();
			tf.width = stage.stageWidth;
			tf.height = stage.stageHeight;
			tf.defaultTextFormat = new TextFormat(null, 24);
			addChild(tf);
			
			log("Testing Amanita Android ANE...");
			log("AndroidNative is supported: " + AndroidNative.isSupported);
			log(AndroidNative.instance.hello());
			
			//Permissions test example:
			AndroidNative.instance.addEventListener(RequestPermissionsResultEvent.ON_REQUEST_PERMISSIONS_RESULT, onRequestPermissionsResult);
			log("PERMISION WRITE: " + AndroidNative.instance.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE"));
			log("RequestionPermissions: "+ AndroidNative.instance.requestPermissions(["android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECEIVE_SMS"]));
			
		}
		
		private function onRequestPermissionsResult( event:RequestPermissionsResultEvent ):void {
			log("RequestPermissionsResult: " + event.value);
			log("PERMISION WRITE: " + AndroidNative.instance.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE"));
		}
		
		private function log(value:String):void {
			trace(value);
			tf.appendText(value+"\n");
			tf.scrollV = tf.maxScrollV;
		}
		
		private function deactivate(e:Event):void 
		{
			// make sure the app behaves well (or exits) when in background
			//NativeApplication.nativeApplication.exit();
		}
		
	}
	
}