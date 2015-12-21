package com.example.hau.weatherapp.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.example.hau.weatherapp.R;
import com.example.hau.weatherapp.model.autocomplete.ResultsAuto;
import com.example.hau.weatherapp.utils.AutocompleteAPI;

/**
 * Created by HAU on 9/7/2015.
 */
public class SearchableProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.hau.weatherapp.provider.SearchableProvider";

    public static final Uri SEARCH_URI = Uri.parse("content://" + AUTHORITY + "/search");

    public static final Uri DETAILS_URI = Uri.parse("content://" + AUTHORITY + "/details");

    private static final int SEARCH = 1;
    private static final int ICON = R.drawable.map_maker;
    private static final int SUGGESTIONS = 2;
    private static final int DETAILS = 3;

    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, "search", SEARCH);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SUGGESTIONS);
        uriMatcher.addURI(AUTHORITY, "details", DETAILS);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c = null;
        MatrixCursor maCursor = null;
        switch (URI_MATCHER.match(uri)) {
            case SEARCH:
                maCursor = new MatrixCursor(new String[]{"description"});
                try {
                    ResultsAuto resultsAuto = AutocompleteAPI.getAutocomplete(selectionArgs[0]);
                    for (int i = 0; i < resultsAuto.getPredictions().size(); i++) {
                        String address = resultsAuto.getPredictions().get(i).getDescription();
                        maCursor.addRow(new String[]{address});
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                c = maCursor;
                break;
            case SUGGESTIONS:
                maCursor = new MatrixCursor(new String[]{"_id",
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_ICON_1});
                try {
                    ResultsAuto resultsAuto = AutocompleteAPI.getAutocomplete(selectionArgs[0]);
                    for (int i = 0; i < resultsAuto.getPredictions().size(); i++) {
                        String address = resultsAuto.getPredictions().get(i).getDescription();
                        maCursor.addRow(new String[]{Integer.toString(i), address, Integer.toString(ICON)});
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                c = maCursor;
                break;
            case DETAILS:
                maCursor = new MatrixCursor(new String[]{"description"});
                try {
                    ResultsAuto resultsAuto = AutocompleteAPI.getAutocomplete(selectionArgs[0]);
                    for (int i = 0; i < resultsAuto.getPredictions().size(); i++) {
                        String address = resultsAuto.getPredictions().get(i).getDescription();
                        maCursor.addRow(new String[]{address});
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                c = maCursor;
                break;
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
