/*
 * This file contains Original Code and/or Modifications of Original Code
 * as defined in and that are subject to the Apache License
 * Version 2.0 (the 'License'). You may not use this file except in
 * compliance with the License. Please obtain a copy of the License at
 * http://opensource.org/licenses/Apache-2.0/ and read it before using this
 * file.
 *
 * The Original Code and all software distributed under the License are
 * distributed on an 'AS IS' basis, WITHOUT WARRANTY OF ANY KIND, EITHER
 * EXPRESS OR IMPLIED, AND APPLE HEREBY DISCLAIMS ALL SUCH WARRANTIES,
 * INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, QUIET ENJOYMENT OR NON-INFRINGEMENT.
 * Please see the License for the specific language governing rights and
 * limitations under the License.
 */

package de.appplant.cordova.plugin.badge;

import java.lang.Math;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Implementation of the badge interface methods.
 */
public class BadgeImpl {

    // The name for the shared preferences key
    private static final String BADGE_KEY = "badge";

    /**
     * Clear the badge number.
     *
     * @param ctx The application context.
     */
    public void clearBadge (Context ctx) {
        saveBadge(0, ctx);
        ShortcutBadger.removeCount(ctx);
    }

    /**
     * Get the badge number.
     *
     * @param ctx      The application context.
	 *
	 * @return The current badge count
     */
    public int getBadge (Context ctx) {
        SharedPreferences settings = getSharedPreferences(ctx);
        int badge = settings.getInt(BADGE_KEY, 0);
        
		return badge;
    }

    /**
     * Set the badge number.
     *
     * @param badge The number to set as the badge number.
     * @param ctx  The application context
     */
    public void setBadge (int badge, Context ctx) {
		badge = Math.max(badge, 0);
        saveBadge(badge, ctx);
        ShortcutBadger.applyCount(ctx, badge);
    }

    /**
     * Persist the badge of the app icon so that `getBadge` is able to return
     * the badge number back to the client.
     *
     * @param badge The badge number to persist.
     * @param ctx   The application context.
     */
    private void saveBadge (int badge, Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();

        editor.putInt(BADGE_KEY, badge);
        editor.apply();
    }

    /**
     * The Local storage for the application.
     */
    private SharedPreferences getSharedPreferences (Context context) {
        return context.getSharedPreferences(BADGE_KEY, Context.MODE_PRIVATE);
    }

}
