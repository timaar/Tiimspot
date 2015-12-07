'use strict';

angular.module('tiimspotApp')
    .factory('PersoonEventSearch', function ($resource) {
        return $resource('api/_search/persoonEvents/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
