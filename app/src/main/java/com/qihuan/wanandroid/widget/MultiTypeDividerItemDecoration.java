package com.qihuan.wanandroid.widget;

import android.content.Context;

import com.drakeet.multitype.MultiTypeAdapter;

import java.util.List;

/**
 * MultiTypeDividerItemDecoration
 *
 * @author qi
 * @since 2020/8/4
 */
@SuppressWarnings("unused")
public class MultiTypeDividerItemDecoration extends DividerItemDecoration {

    private final MultiTypeAdapter adapter;
    private final Class<?>[] dividerClasses;

    public MultiTypeDividerItemDecoration(Context context, int orientation, MultiTypeAdapter adapter, Class<?>[] dividerClasses) {
        this(context, orientation, 0, 0, adapter, dividerClasses);
    }

    public MultiTypeDividerItemDecoration(Context context, int orientation, int paddingStart, int paddingEnd, MultiTypeAdapter adapter, Class<?>[] dividerClasses) {
        super(context, orientation, paddingStart, paddingEnd);
        this.adapter = adapter;
        this.dividerClasses = dividerClasses;
    }

    @Override
    protected boolean showDivider(int position) {
        if (adapter == null || dividerClasses == null) {
            return true;
        }
        List<?> items = adapter.getItems();
        boolean should = false;
        for (int i = 0; !should && i < dividerClasses.length; i++) {
            should = position + 1 < items.size()
                    && items.get(position).getClass().isAssignableFrom(dividerClasses[i])
                    && (items.get(position + 1).getClass().isAssignableFrom(dividerClasses[i]));
        }
        return should;
    }
}
