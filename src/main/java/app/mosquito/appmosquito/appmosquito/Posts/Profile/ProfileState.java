package app.mosquito.appmosquito.appmosquito.Posts.Profile;


public enum ProfileState {

    FAZENDO {
        @Override
        public String toString() {
            return "fazendo";
        }
    }, FINALIZADO {
        @Override
        public String toString() {
            return "finalizado";
        }
    }
}
