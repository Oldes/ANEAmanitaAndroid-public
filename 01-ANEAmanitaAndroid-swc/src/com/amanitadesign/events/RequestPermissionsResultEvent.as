package com.amanitadesign.events
{
	import flash.events.Event;
	
	public class RequestPermissionsResultEvent extends Event
	{
		public static const ON_REQUEST_PERMISSIONS_RESULT = "onRequestPermissionsResult";
		private var mValue:String;
		
		public function RequestPermissionsResultEvent(type:String, value:String="", bubbles:Boolean=false, cancelable:Boolean=false)
		{
			mValue = value;
			super(type, bubbles, cancelable);
			//trace("RequestPermissionsResultEvent: " + value);
		}
		[Inline] public final function get value():String {
			return mValue;
		}
	}
}