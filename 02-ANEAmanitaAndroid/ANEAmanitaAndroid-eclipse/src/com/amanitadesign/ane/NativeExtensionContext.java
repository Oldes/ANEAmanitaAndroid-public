/*
 * Copyright (C) <year> <copyright holders>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.amanitadesign.ane;

import java.util.HashMap;
import java.util.Map;

import com.amanitadesign.ane.functions.*;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;


public class NativeExtensionContext extends FREContext 
{
    public static FREContext FREContext;

    public NativeExtensionContext()
    {

    }

    @Override
	public void dispose() {
		Log.i(NativeExtension.TAG, "Context disposed.");
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
		functions.put("init", new InitFunction());
		functions.put("hello", new HelloFunction());
		return functions;
	}

}
