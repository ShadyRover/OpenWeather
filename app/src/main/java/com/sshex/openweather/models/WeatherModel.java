package com.sshex.openweather.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class WeatherModel implements Serializable {

    private Coord coord;

    private List<Weather> weather = new ArrayList<Weather>();

    private String base;

    private Main main;

    private Wind wind;

    private Clouds clouds;

    private Integer dt;

    private Sys sys;

    private Integer id;

    private String name;

    private Integer cod;

    /**
     * @return The coord
     */
    public Coord getCoord() {
        return coord;
    }

    /**
     * @param coord The coord
     */
    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    /**
     * @return The weather
     */
    public List<Weather> getWeather() {
        return weather;
    }

    /**
     * @param weather The weather
     */
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    /**
     * @return The base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * @return The main
     */
    public Main getMain() {
        return main;
    }

    /**
     * @param main The main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * @return The wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * @param wind The wind
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * @return The clouds
     */
    public Clouds getClouds() {
        return clouds;
    }

    /**
     * @param clouds The clouds
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * @return The dt
     */
    public Integer getDt() {
        return dt;
    }

    /**
     * @param dt The dt
     */
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    /**
     * @return The sys
     */
    public Sys getSys() {
        return sys;
    }

    /**
     * @param sys The sys
     */
    public void setSys(Sys sys) {
        this.sys = sys;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The cod
     */
    public Integer getCod() {
        return cod;
    }

    /**
     * @param cod The cod
     */
    public void setCod(Integer cod) {
        this.cod = cod;
    }
    public class Clouds {

        private Integer all;

        /**
         * @return The all
         */
        public Integer getAll() {
            return all;
        }

        /**
         * @param all The all
         */
        public void setAll(Integer all) {
            this.all = all;
        }

    }

    public class Coord {

        private Double lon;
        private Double lat;

        /**
         * @return The lon
         */
        public Double getLon() {
            return lon;
        }

        /**
         * @param lon The lon
         */
        public void setLon(Double lon) {
            this.lon = lon;
        }

        /**
         * @return The lat
         */
        public Double getLat() {
            return lat;
        }

        /**
         * @param lat The lat
         */
        public void setLat(Double lat) {
            this.lat = lat;
        }

    }

    @DatabaseTable(tableName = "WeatherTable")
    public static class Main {

        @DatabaseField
        private Double temp;
        @DatabaseField
        private Double pressure;
        @DatabaseField
        private Integer humidity;
        @DatabaseField
        private Double tempMin;
        @DatabaseField
        private Double tempMax;

        private Double seaLevel;

        private Double grndLevel;

        public Main() {
        }

        public Main(double temp, double press, int water, double tempMin, double tempMax) {
            setTemp(temp);
            setPressure(press);
            setHumidity(water);
            setTempMax(tempMax);
            setTempMin(tempMin);
        }
        public Main(double temp, double press, int water) {
            setTemp(temp);
            setPressure(press);
            setHumidity(water);
        }
        /**
         * @return The temp
         */
        public Double getTemp() {
            return temp;
        }

        /**
         * @param temp The temp
         */
        public void setTemp(Double temp) {
            this.temp = temp;
        }

        /**
         * @return The pressure
         */
        public Double getPressure() {
            return pressure;
        }

        /**
         * @param pressure The pressure
         */
        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        /**
         * @return The humidity
         */
        public Integer getHumidity() {
            return humidity;
        }

        /**
         * @param humidity The humidity
         */
        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        /**
         * @return The tempMin
         */
        public Double getTempMin() {
            return tempMin;
        }

        /**
         * @param tempMin The temp_min
         */
        public void setTempMin(Double tempMin) {
            this.tempMin = tempMin;
        }

        /**
         * @return The tempMax
         */
        public Double getTempMax() {
            return tempMax;
        }

        /**
         * @param tempMax The temp_max
         */
        public void setTempMax(Double tempMax) {
            this.tempMax = tempMax;
        }

        /**
         * @return The seaLevel
         */
        public Double getSeaLevel() {
            return seaLevel;
        }

        /**
         * @param seaLevel The sea_level
         */
        public void setSeaLevel(Double seaLevel) {
            this.seaLevel = seaLevel;
        }

        /**
         * @return The grndLevel
         */
        public Double getGrndLevel() {
            return grndLevel;
        }

        /**
         * @param grndLevel The grnd_level
         */
        public void setGrndLevel(Double grndLevel) {
            this.grndLevel = grndLevel;
        }

    }

    public class Sys {

        private Double message;

        private String country;

        private Integer sunrise;

        private Integer sunset;

        /**
         * @return The message
         */
        public Double getMessage() {
            return message;
        }

        /**
         * @param message The message
         */
        public void setMessage(Double message) {
            this.message = message;
        }

        /**
         * @return The country
         */
        public String getCountry() {
            return country;
        }

        /**
         * @param country The country
         */
        public void setCountry(String country) {
            this.country = country;
        }

        /**
         * @return The sunrise
         */
        public Integer getSunrise() {
            return sunrise;
        }

        /**
         * @param sunrise The sunrise
         */
        public void setSunrise(Integer sunrise) {
            this.sunrise = sunrise;
        }

        /**
         * @return The sunset
         */
        public Integer getSunset() {
            return sunset;
        }

        /**
         * @param sunset The sunset
         */
        public void setSunset(Integer sunset) {
            this.sunset = sunset;
        }

    }

    public class Weather {

        private Integer id;

        private String main;

        private String description;
        private String icon;

        /**
         * @return The id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @return The main
         */
        public String getMain() {
            return main;
        }

        /**
         * @param main The main
         */
        public void setMain(String main) {
            this.main = main;
        }

        /**
         * @return The description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description The description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return The icon
         */
        public String getIcon() {
            return icon;
        }

        /**
         * @param icon The icon
         */
        public void setIcon(String icon) {
            this.icon = icon;
        }

    }

    public class Wind {
        private Double speed;
        private Double deg;

        /**
         * @return The speed
         */
        public Double getSpeed() {
            return speed;
        }

        /**
         * @param speed The speed
         */
        public void setSpeed(Double speed) {
            this.speed = speed;
        }

        /**
         * @return The deg
         */
        public Double getDeg() {
            return deg;
        }

        /**
         * @param deg The deg
         */
        public void setDeg(Double deg) {
            this.deg = deg;
        }

    }
}