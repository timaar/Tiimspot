'use strict';

angular.module('tiimspotApp')
    .controller('ContactinfoController', function ($scope, $state, $modal, Contactinfo, ContactinfoSearch, ParseLinks) {
      
        $scope.contactinfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Contactinfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.contactinfos.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.contactinfos = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ContactinfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.contactinfos = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.contactinfo = {
                email: null,
                telefoonnummer: null,
                id: null
            };
        };
    });
