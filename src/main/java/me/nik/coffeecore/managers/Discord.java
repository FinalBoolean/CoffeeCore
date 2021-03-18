package me.nik.coffeecore.managers;

import me.nik.coffeecore.CoffeeCore;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Discord {

    private static final String AVATAR_URL = "https://mc-heads.net/avatar/";

    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static final String IMAGE_URL = "https://github.com/NikV2/AliceAPI/blob/master/img/CoffeeLogoTrans.png?raw=true";
    private static final String USERNAME = "CoffeeCore";
    private static long LAST_SENT = System.currentTimeMillis();
    private final String url;
    private final List<EmbedObject> embeds = new ArrayList<>();

    public Discord(String url) {
        this.url = url;
    }

    public static void sendCrashAlert(final Player player) {

        final String name = player.getName();

        Discord discord = new Discord(CoffeeCore.getInstance().getConfiguration().get().getString("discord_url"));

        discord.addEmbed(new Discord.EmbedObject()
                .setTitle("Anti Crash")
                .setColor(Color.RED)
                .setDescription(name + " Tried to crash the server")
                .setPlayerAsImage(name));

        discord.execute();
    }

    public void addEmbed(EmbedObject embed) {
        this.embeds.add(embed);
    }

    public void execute() {
        if (this.url.isEmpty() || (System.currentTimeMillis() - LAST_SENT) < 1250) return;

        LAST_SENT = System.currentTimeMillis();

        executor.execute(() -> {

            JSONObject json = new JSONObject();

            json.put("avatar_url", IMAGE_URL);

            json.put("username", USERNAME);

            if (!this.embeds.isEmpty()) {
                List<JSONObject> embedObjects = new ArrayList<>();

                for (EmbedObject embed : this.embeds) {
                    JSONObject jsonEmbed = new JSONObject();

                    jsonEmbed.put("title", embed.getTitle());

                    if (embed.getDescription() != null) {
                        jsonEmbed.put("description", embed.getDescription());
                    }

                    if (embed.getImageUrl() != null) {
                        JSONObject jsonImage = new JSONObject();

                        jsonImage.put("url", embed.getImageUrl());
                        jsonEmbed.put("thumbnail", jsonImage);
                    }

                    if (embed.getColor() != null) {
                        Color color = embed.getColor();
                        int rgb = color.getRed();
                        rgb = (rgb << 8) + color.getGreen();
                        rgb = (rgb << 8) + color.getBlue();

                        jsonEmbed.put("color", rgb);
                    }

                    List<EmbedObject.Field> fields = embed.getFields();

                    List<JSONObject> jsonFields = new ArrayList<>();
                    for (EmbedObject.Field field : fields) {
                        JSONObject jsonField = new JSONObject();

                        jsonField.put("name", field.getName());
                        jsonField.put("value", field.getValue());

                        jsonFields.add(jsonField);
                    }

                    jsonEmbed.put("fields", jsonFields.toArray());
                    embedObjects.add(jsonEmbed);
                }

                json.put("embeds", embedObjects.toArray());
            }

            try {
                URL url = new URL(this.url);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.addRequestProperty("Content-Type", "application/json");
                connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                OutputStream stream = connection.getOutputStream();
                stream.write(json.toString().getBytes());
                stream.flush();
                stream.close();

                connection.getInputStream().close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static class EmbedObject {
        private final List<Field> fields = new ArrayList<>();
        private String title;
        private String imageUrl;
        private String description;
        private Color color;

        public String getTitle() {
            return title;
        }

        public EmbedObject setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public EmbedObject setPlayerAsImage(String player) {
            this.imageUrl = AVATAR_URL + player;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public EmbedObject setDescription(String description) {
            this.description = description;
            return this;
        }

        public Color getColor() {
            return color;
        }

        public EmbedObject setColor(Color color) {
            this.color = color;
            return this;
        }

        public List<Field> getFields() {
            return fields;
        }

        public EmbedObject addField(String name, String value) {
            this.fields.add(new Field(name, value));
            return this;
        }

        public EmbedObject addFields(Collection<String> names, Collection<String> values) {
            for (String name : names) {
                for (String value : values) {
                    this.fields.add(new Field(name, value));
                }
            }
            return this;
        }

        private static class Field {
            private final String name;
            private final String value;

            private Field(String name, String value) {
                this.name = name;
                this.value = value;
            }

            private String getName() {
                return name;
            }

            private String getValue() {
                return value;
            }
        }
    }

    private static class JSONObject {

        private final HashMap<String, Object> map = new HashMap<>();

        void put(String key, Object value) {
            if (value != null) {
                map.put(key, value);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            builder.append("{");

            int i = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                Object val = entry.getValue();
                builder.append(quote(entry.getKey())).append(":");

                if (val instanceof String) {
                    builder.append(quote(String.valueOf(val)));
                } else if (val instanceof Integer) {
                    builder.append(Integer.valueOf(String.valueOf(val)));
                } else if (val instanceof Boolean) {
                    builder.append(val);
                } else if (val instanceof JSONObject) {
                    builder.append(val.toString());
                } else if (val.getClass().isArray()) {
                    builder.append("[");
                    int len = Array.getLength(val);
                    for (int j = 0; j < len; j++) {
                        builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                    }
                    builder.append("]");
                }

                builder.append(++i == entrySet.size() ? "}" : ",");
            }

            return builder.toString();
        }

        private String quote(String string) {
            return "\"" + string + "\"";
        }
    }
}