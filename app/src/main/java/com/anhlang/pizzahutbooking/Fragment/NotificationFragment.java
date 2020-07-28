package com.anhlang.pizzahutbooking.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anhlang.pizzahutbooking.Adapter.NotificationAdapter;
import com.anhlang.pizzahutbooking.Object.Notification;
import com.anhlang.pizzahutbooking.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;



public class NotificationFragment extends Fragment {

    DatabaseReference reference;
    ArrayList<Notification> arrayList;
    ListView listView;
    ImageView btn_more;
    ChipGroup chipGroup;
    Chip chip_all, chip_watched, chip_watching, chip_ordered, chip_removed;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        reference = FirebaseDatabase.getInstance().getReference().child("NOTIFICATION");

        listView = view.findViewById(R.id.ls_notification);
        btn_more = view.findViewById(R.id.btn_more);
        chipGroup = view.findViewById(R.id.chip_group);
        chip_all = view.findViewById(R.id.chip_all);
        chip_watched = view.findViewById(R.id.chip_watched);
        chip_watching = view.findViewById(R.id.chip_watching);
        chip_ordered = view.findViewById(R.id.chip_ordered);
        chip_removed = view.findViewById(R.id.chip_removed);

        setChip();

        if (chip_all.isChecked() && !chip_watched.isChecked() && !chip_watching.isChecked() && !chip_ordered.isChecked() && !chip_removed.isChecked()){
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Notification notification = dataSnapshot.getValue(Notification.class);
                        arrayList.add(0, notification);
                    }
                    NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        chip_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !chip_watched.isChecked() && !chip_watching.isChecked() && !chip_ordered.isChecked() && !chip_removed.isChecked()){
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            arrayList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Notification notification = dataSnapshot.getValue(Notification.class);
                                arrayList.add(0, notification);
                            }
                            NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        chip_watched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (chip_all.isChecked()) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(true)) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else if (chip_ordered.isChecked()) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(true) && dataSnapshot.child("type").getValue().equals("order")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(true) && dataSnapshot.child("type").getValue().equals("remove")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });
        chip_watching.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (chip_all.isChecked()) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(false)) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else if (chip_ordered.isChecked()) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(false) && dataSnapshot.child("type").getValue().equals("order")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(false) && dataSnapshot.child("type").getValue().equals("remove")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });

        chip_ordered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (chip_all.isChecked()) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("type").getValue().equals("order")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else if (chip_watched.isChecked()) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(true) && dataSnapshot.child("type").getValue().equals("order")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(false) && dataSnapshot.child("type").getValue().equals("order")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });

        chip_removed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (chip_all.isChecked()) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("type").getValue().equals("remove")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else if (chip_watched.isChecked()) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(true) && dataSnapshot.child("type").getValue().equals("remove")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.child("read").getValue().equals(false) && dataSnapshot.child("type").getValue().equals("remove")) {
                                        Notification notification = dataSnapshot.getValue(Notification.class);
                                        arrayList.add(0, notification);
                                    }
                                }
                                NotificationAdapter adapter = new NotificationAdapter(getActivity(), arrayList, R.layout.list_notification);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getActivity(), btn_more, Gravity.RIGHT);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup_menu_notification, popup.getMenu());
                Object menuHelper;
                Class[] argTypes;
                try {
                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                    fMenuHelper.setAccessible(true);
                    menuHelper = fMenuHelper.get(popup);
                    argTypes = new Class[]{boolean.class};
                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                } catch (Exception e) {
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });
                popup.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("NOTIFICATION").child(arrayList.get(position).getId()).child("read");
                reference.setValue(true);
            }
        });

        return  view;
    }

    private boolean menuItemClicked(MenuItem item){
        if(item.getItemId() == R.id.mark_read_all){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("NOTIFICATION");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            dataSnapshot.getRef().child("read").setValue(true);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return true;
    }

    private void setChip(){

        chip_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chip_all.isChecked()){
                    chip_watched.setChecked(false);
                    chip_watching.setChecked(false);
                    chip_removed.setChecked(false);
                    chip_ordered.setChecked(false);
                }else{
                    chip_all.setChecked(true);
                }
            }
        });


        chip_watched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chip_watched.isChecked()){
                    chip_watching.setChecked(false);
                    if(chip_removed.isChecked() || chip_ordered.isChecked()){
                        chip_all.setChecked(false);
                    }
                }else{
                    chip_watched.setChecked(true);
                }
            }
        });

        chip_watching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chip_watching.isChecked()){
                    chip_watched.setChecked(false);
                    if(chip_removed.isChecked() || chip_ordered.isChecked()){
                        chip_all.setChecked(false);
                    }
                }else{
                    chip_watching.setChecked(true);
                }
            }
        });

        chip_ordered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chip_ordered.isChecked()){
                    chip_removed.setChecked(false);
                    if(chip_watched.isChecked() || chip_watching.isChecked()){
                        chip_all.setChecked(false);
                    }
                }else{
                    chip_ordered.setChecked(true);
                }
            }
        });

        chip_removed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chip_removed.isChecked()){
                    chip_ordered.setChecked(false);
                    if(chip_watched.isChecked() || chip_watching.isChecked()){
                        chip_all.setChecked(false);
                    }
                }else{
                    chip_removed.setChecked(true);
                }
            }
        });
    }

}
