package com.amanitadesign
{
	import flash.events.EventDispatcher;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;

	import flash.events.Event;
	import flash.events.StatusEvent;
	import com.amanitadesign.events.RequestPermissionsResultEvent;
	
	public class AndroidNative extends EventDispatcher
	{
		
		private static var _instance:AndroidNative;
		private var extContext:ExtensionContext;
		
		
		public function AndroidNative( enforcer:SingletonEnforcer ) {
			super();
			
			extContext = ExtensionContext.createExtensionContext( "com.amanitadesign.AndroidNative", "" );
			
			if ( !extContext ) {
				throw new Error( "AndroidNative extension is not supported on this platform." );
			}
			extContext.addEventListener( StatusEvent.STATUS, onStatusHandler );
		}
		
		/** Extension is supported on Android devices. */
		public static function get isSupported() : Boolean
		{
			return Capabilities.manufacturer.indexOf("Android") != -1;
		}
		
		
		private function init():void {
			extContext.call( "init" );
			
		}

		private function onStatusHandler( event:StatusEvent ):void {
			trace("onStatusHandler: " + event)
			var e:Event;
			
			switch(event.code) {
				case RequestPermissionsResultEvent.ON_REQUEST_PERMISSIONS_RESULT:
					e = new RequestPermissionsResultEvent(event.code, event.level);
					break;
					
			}
			if(e) {
				this.dispatchEvent(e);
			}
			//
			//
		}

		
		/**
		 * Cleans up the instance of the native extension. 
		 */		
		public function dispose():void { 
			extContext.dispose(); 
		}
		
		
		public static function get instance():AndroidNative {
			if ( !_instance ) {
				_instance = new AndroidNative( new SingletonEnforcer() );
				_instance.init();
			}
			return _instance;
		}
		
		
		//----------------------------------------
		//
		// Public Methods
		//
		//----------------------------------------
		
		public function hello():String {
			return extContext.call( "hello" ) as String;
		}
		
		public function checkPermission(permision:String): Boolean {
			return extContext.call("checkPermission", permision) as Boolean;
		}
		public function requestPermissions(permisions:Array): Boolean {
			return extContext.call("requestPermissions", permisions) as Boolean;
		}
		
	}
}

class SingletonEnforcer {}