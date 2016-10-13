package com.amanitadesign
{
	import flash.events.EventDispatcher;
	import flash.events.ErrorEvent;
	import flash.events.Event;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;
	
	import com.amanitadesign.events.AirGooglePlayGamesEvent;
	

	
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
		
		
		private function onStatusHandler( event:StatusEvent ):void {
			trace("onStatusHandler: " + event)
			var e:Event;
			switch(event.code) {
				case AirGooglePlayGamesEvent.ON_SIGN_IN_SUCCESS:
				case AirGooglePlayGamesEvent.ON_SIGN_IN_FAIL:
				case AirGooglePlayGamesEvent.ON_SIGN_OUT_SUCCESS:
				case AirGooglePlayGamesEvent.ON_OPEN_SNAPSHOT_READY:
				case AirGooglePlayGamesEvent.ON_OPEN_SNAPSHOT_FAILED:
					e = new AirGooglePlayGamesEvent(event.code, event.level);
					break;
					
			}
			if(e) {
				this.dispatchEvent(e);
			}
			//
			//
		}

		
		
		//----------------------------------------
		//
		// Public Methods
		//
		//----------------------------------------
		
		public function hello():String {
			return extContext.call( "hello" ) as String;
		}
		
		/********************************************************************************************************
		 * 
		 *       GOOGLE API  ************************************************************************************
		 ********************************************************************************************************/

		public function isSignedIn():Boolean
		{
			var signedIn:Boolean = false;
			if (AndroidNative.isSupported)
			{
				signedIn = extContext.call("isSignedIn") as Boolean;
			}
			return signedIn;
		}
		
		public function signIn():void
		{
			if (AndroidNative.isSupported)
			{
				extContext.call("signIn");
			}
		}
		
		public function signOut():void
		{
			if (AndroidNative.isSupported)
			{
				extContext.call("signOut");
			}
		}
		
		public function reportAchievement(achievementId:String, percent:Number = 0):void
		{
			if (AndroidNative.isSupported)
			{
				extContext.call("reportAchievement", achievementId, percent);
			}
		}
		
		public function showStandardAchievements():void
		{
			if (AndroidNative.isSupported)
			{
				extContext.call("showStandardAchievements");
			}
		} 
		
		
		
		
	}
}

class SingletonEnforcer {}