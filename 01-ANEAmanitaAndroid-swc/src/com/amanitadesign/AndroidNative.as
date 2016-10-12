package com.amanitadesign
{
	import flash.events.EventDispatcher;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;

	
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
		
		
		//----------------------------------------
		//
		// Public Methods
		//
		//----------------------------------------
		
		public function hello():String {
			return extContext.call( "hello" ) as String;
		}
		
	}
}

class SingletonEnforcer {}